package com.fastma.util;

public class Utilities {
	// A utility class with only static methods will never be instantiated. Make
	// sure it's the case with a private constructor to prevent the construction of
	// a useless object.
	private Utilities() {
		throw new AssertionError();
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}

	public static int getRandIntBetween(int min, int max) {
		return (int) ((Math.random() * ((max - min) + 1)) + min);
	}

}
