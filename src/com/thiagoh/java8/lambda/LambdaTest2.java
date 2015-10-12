package com.thiagoh.java8.lambda;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

public class LambdaTest2 {

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

			Predicate<String> p = ((Predicate<String>) String::isEmpty).negate().and(s -> {

				return (s.charAt(0) >= 97 && s.charAt(0) <= 122) || (s.charAt(0) >= 65 && s.charAt(0) <= 90);

			}).and((s) -> {

				if (b.get()) {

					return true;

				} else {

					return speech.chars().parallel().allMatch(c -> c >= 97 && c <= 122); // lower
																							// case
				}
			});

			if (p.test(speech)) {
				System.out.println("The word passed the test!: " + speech);
			} else {
				System.out.println("The word has not passed the test!: " + speech);
			}

			return speech;
		};

		CompletableFuture<Object> f = CompletableFuture.runAsync(() -> {

			act.speak("azAZ");
			b.set(false);

		}).thenApplyAsync((x) -> {

			try {

				Thread.sleep(3000);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return act.speak("azAZ");

		}).thenApplyAsync(x -> {

			System.out.println("Value: " + x);

			return act.speak("az");

		}).thenApplyAsync(x -> {

			System.out.println("Value: " + x);

			return x;
		});

		try {

			f.get();

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
