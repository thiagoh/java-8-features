package com.thiagoh.java8.lambda;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

public class LambdaTest1 {

	public static Thread setTimeout(Runnable r, long millis) {

		Thread t = new Thread(() -> {

			try {

				Thread.sleep(millis);

			} catch (Exception e) {
				e.printStackTrace();
			}

			r.run();
		});

		t.start();

		return t;
	}

	public static class BooleanWrapper {
		private boolean b;

		public BooleanWrapper(boolean b) {
			this.b = b;
		}

		public void set(boolean b) {
			this.b = b;
		}

		public boolean get() {
			return b;
		}
	}

	public static void main(String[] args) {

		BooleanWrapper b = new BooleanWrapper(false);

		AnimalLanguage act = speech -> {

			speech.chars().forEach(System.out::println);

			Predicate<String> p = ((Predicate<String>) String::isEmpty).negate().and(s -> {

				return (s.charAt(0) >= 97 && s.charAt(0) <= 122) || (s.charAt(0) >= 65 && s.charAt(0) <= 90);

			}).and((s) -> {

				if (b.get()) {

					return true;

				} else {

					return speech.chars().parallel().allMatch(c -> c >= 65 && c <= 90);
				}
			});

			if (p.test(speech)) {
				System.out.println("The word passed the test!: " + speech);
			} else {
				System.out.println("The word has not passed the test!: " + speech);
			}
			
			return speech;
		};

		CompletableFuture<Void> f = CompletableFuture.runAsync(() -> {});
		
		f.thenRun(() -> {

			System.out.println("nhaaa");

			setTimeout(() -> {

				act.speak("azAZ");
				b.set(false);

				f.thenRun(() -> {

					setTimeout(() -> act.speak("azZ"), 2000);
					setTimeout(() -> b.set(true), 3000);
				});

			}, 2000);

			setTimeout(() -> b.set(true), 1000);
		});

		try {

			f.get();

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
