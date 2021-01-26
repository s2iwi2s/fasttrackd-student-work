package com.cooksys.ftd.assignments.control;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * The Fibonacci sequence is simply and recursively defined: the first two
 * elements are `1`, and every other element is equal to the sum of its two
 * preceding elements. For example:
 * <p>
 * [1, 1] => [1, 1, 1 + 1] => [1, 1, 2] => [1, 1, 2, 1 + 2] => [1, 1, 2, 3] =>
 * [1, 1, 2, 3, 2 + 3] => [1, 1, 2, 3, 5] => ...etc
 */
public class Fibonacci {

	/**
	 * Calculates the value in the Fibonacci sequence at a given index. For example,
	 * `atIndex(0)` and `atIndex(1)` should return `1`, because the first two
	 * elements of the sequence are both `1`.
	 *
	 * @param i the index of the element to calculate
	 * @return the calculated element
	 * @throws IllegalArgumentException if the given index is less than zero
	 */
	public static int atIndex(int i) throws IllegalArgumentException {
		if (i < 0) {
			throw new IllegalArgumentException();
		}
		int[] temp = getFibAry(i);
		return temp[i];
	}
	private static int[] getFibAry(int i) {
		if (i < 0) {
			throw new IllegalArgumentException();
		}
		int[] series = new int[i + 2];
		series[0] = 1;
		series[1] = 1;
		for (int idx = 2; idx <= i; idx++) {
			series[idx] = series[idx - 2] + series[idx - 1];
		}
		return series;
	}

	/**
	 * Calculates a slice of the fibonacci sequence, starting from a given start
	 * index (inclusive) and ending at a given end index (exclusive).
	 *
	 * @param start the starting index of the slice (inclusive)
	 * @param end   the ending index of the slice(exclusive)
	 * @return the calculated slice as an array of int elements
	 * @throws IllegalArgumentException if either the given start or end is
	 *                                  negative, or if the given end is less than
	 *                                  the given start
	 */
	public static int[] slice(int start, int end) throws IllegalArgumentException {
		if (start < 0 || end < 0) {
			throw new IllegalArgumentException();
		}
		// 1st solution
//		int[] result = {};
//		for (int i = start; i < end; i++) {
//			int[] temp = new int[result.length + 1];
//			for(int j = 0 ; j < result.length ; j++) {
//				temp[j] = result[j];
//			}
//			temp[result.length] = atIndex(i);
//			result = temp;
//		}

		// 2nd Solution runs faster. Loops
		// in method atIndex makes it slower.
		// In general. They are both good solution.
		int[] temp = getFibAry(end);
		int[] result = new int[end - start];
		int x = 0;
		for (int i = start; i < end; i++) {
			result[x++] = temp[i];
		}

		return result;
	}

	/**
	 * Calculates the beginning of the fibonacci sequence, up to a given count.
	 *
	 * @param count the number of elements to calculate
	 * @return the beginning of the fibonacci sequence, up to the given count, as an
	 *         array of int elements
	 * @throws IllegalArgumentException if the given count is negative
	 */
	public static int[] fibonacci(int count) throws IllegalArgumentException {
		if(count < 0) {
			throw new IllegalArgumentException();
		}
		
		return slice(0, count);
	}
}
