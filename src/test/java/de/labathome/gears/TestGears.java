package de.labathome.gears;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.fraction.Fraction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestGears {

	/**
	 * Test the case "level 1" for the correct solution.
	 */
	@Test
	void testGears1() {

		Fraction[] transmissionRatios = {
				new Fraction(1, 2),
				new Fraction(1, 1),
				new Fraction(5, 2)
		};

		int minTeethSum = 10;
		int maxTeethSum = 50;
		Fraction minModule = new Fraction(1, 1);
		Fraction maxModule = new Fraction(1, 1);
		List<int[][]> teethCounts = new LinkedList<>();
		Fraction[][] result = Gears.findToothcounts(transmissionRatios,
				minTeethSum, maxTeethSum,
				minModule, maxModule, teethCounts);

		// check that a unique solution was found
		Assertions.assertNotNull(result[0]);

		// check that the unique solution has the same ratios as the request
		for (int idxRatio = 0; idxRatio < transmissionRatios.length; ++idxRatio) {
			Assertions.assertEquals(0, transmissionRatios[idxRatio].compareTo(result[0][idxRatio]));
		}

		// check that the actual teeth counts are correct
		Assertions.assertNotNull(teethCounts);
		Assertions.assertEquals(1, teethCounts.size());
		Assertions.assertArrayEquals(new int[] {14, 28}, teethCounts.get(0)[0]);
		Assertions.assertArrayEquals(new int[] {21, 21}, teethCounts.get(0)[1]);
		Assertions.assertArrayEquals(new int[] {30, 12}, teethCounts.get(0)[2]);
	}

	/**
	 * Test the case "level 2a" for the correct solution.
	 */
	@Test
	void testGears2() {

		Fraction[] transmissionRatios = {
				new Fraction(10, 10),
				new Fraction(12, 10),
				new Fraction(14, 10)
		};

		int minTeethSum = 10;
		int maxTeethSum = 200;
		Fraction minModule = new Fraction(1, 1);
		Fraction maxModule = new Fraction(1, 1);
		List<int[][]> teethCounts = new LinkedList<>();
		Fraction[][] result = Gears.findToothcounts(transmissionRatios,
				minTeethSum, maxTeethSum,
				minModule, maxModule, teethCounts);

		// check that a unique solution was found
		Assertions.assertNotNull(result[0]);

		// check that the unique solution has the same ratios as the request
		for (int idxRatio = 0; idxRatio < transmissionRatios.length; ++idxRatio) {
			Assertions.assertEquals(0, transmissionRatios[idxRatio].compareTo(result[0][idxRatio]));
		}

		// check that the actual teeth counts are correct
		Assertions.assertNotNull(teethCounts);
		Assertions.assertEquals(1, teethCounts.size());
		Assertions.assertArrayEquals(new int[] {66, 66}, teethCounts.get(0)[0]);
		Assertions.assertArrayEquals(new int[] {72, 60}, teethCounts.get(0)[1]);
		Assertions.assertArrayEquals(new int[] {77, 55}, teethCounts.get(0)[2]);
	}

	/**
	 * Test if indeed no solution is found for the case "level 2b".
	 */
	@Test
	void testGears3() {

		Fraction[] transmissionRatios = {
				new Fraction(16, 10),
				new Fraction(18, 10),
				new Fraction(20, 10)
		};

		int minTeethSum = 10;
		int maxTeethSum = 200;
		Fraction minModule = new Fraction(1, 1);
		Fraction maxModule = new Fraction(1, 1);
		List<int[][]> teethCounts = new LinkedList<>();
		Fraction[][] result = Gears.findToothcounts(transmissionRatios,
				minTeethSum, maxTeethSum,
				minModule, maxModule, teethCounts);

		// check that actually no solution was found
		Assertions.assertNull(result[0]);

		// check that the actually no teeth counts are found
		Assertions.assertNotNull(teethCounts);
		Assertions.assertEquals(0, teethCounts.size());
	}

	/**
	 * Test for the correct solution in case "left column".
	 */
	@Test
	void testGears4() {

		Fraction[] transmissionRatios = {
				new Fraction(10, 14),
				new Fraction(14, 14),
				new Fraction(18, 14)
			};

		int minTeethSum = 10;
		int maxTeethSum = 100;
		Fraction minModule = new Fraction(1, 1);
		Fraction maxModule = new Fraction(1, 1);
		List<int[][]> teethCounts = new LinkedList<>();
		Fraction[][] result = Gears.findToothcounts(transmissionRatios,
				minTeethSum, maxTeethSum,
				minModule, maxModule, teethCounts);

		// check that a unique solution was found
		Assertions.assertNotNull(result[0]);

		// check that the unique solution has the same ratios as the request
		for (int idxRatio = 0; idxRatio < transmissionRatios.length; ++idxRatio) {
			Assertions.assertEquals(0, transmissionRatios[idxRatio].compareTo(result[0][idxRatio]));
		}

		// check that the actual teeth counts are correct
		Assertions.assertNotNull(teethCounts);
		Assertions.assertEquals(2, teethCounts.size());

		Assertions.assertArrayEquals(new int[] {20, 28}, teethCounts.get(0)[0]);
		Assertions.assertArrayEquals(new int[] {24, 24}, teethCounts.get(0)[1]);
		Assertions.assertArrayEquals(new int[] {27, 21}, teethCounts.get(0)[2]);

		Assertions.assertArrayEquals(new int[] {40, 56}, teethCounts.get(1)[0]);
		Assertions.assertArrayEquals(new int[] {48, 48}, teethCounts.get(1)[1]);
		Assertions.assertArrayEquals(new int[] {54, 42}, teethCounts.get(1)[2]);
	}

	/**
	 * Test for the correct solution in case "right column".
	 */
	@Test
	void testGears5() {

		Fraction[] transmissionRatios = {
				new Fraction(12, 12),
				new Fraction(16, 12),
				new Fraction(20, 12)
		};

		int minTeethSum = 10;
		int maxTeethSum = 100;
		Fraction minModule = new Fraction(1, 1);
		Fraction maxModule = new Fraction(1, 1);
		List<int[][]> teethCounts = new LinkedList<>();
		Fraction[][] result = Gears.findToothcounts(transmissionRatios,
				minTeethSum, maxTeethSum,
				minModule, maxModule, teethCounts);

		// check that a unique solution was found
		Assertions.assertNotNull(result[0]);

		// check that the unique solution has the same ratios as the request
		for (int idxRatio = 0; idxRatio < transmissionRatios.length; ++idxRatio) {
			Assertions.assertEquals(0, transmissionRatios[idxRatio].compareTo(result[0][idxRatio]));
		}

		// check that the actual teeth counts are correct
		Assertions.assertNotNull(teethCounts);
		Assertions.assertEquals(1, teethCounts.size());

		Assertions.assertArrayEquals(new int[] {28, 28}, teethCounts.get(0)[0]);
		Assertions.assertArrayEquals(new int[] {32, 24}, teethCounts.get(0)[1]);
		Assertions.assertArrayEquals(new int[] {35, 21}, teethCounts.get(0)[2]);
	}

	/**
	 * Test that a no-solution case can be solved without having teethCounts specified.
	 */
	@Test
	void testGears6() {

		Fraction[] transmissionRatios = {
				new Fraction(16, 10),
				new Fraction(18, 10),
				new Fraction(20, 10)
		};

		Fraction[][] result = Gears.findToothcounts(transmissionRatios);

		// check that actually no solution was found
		Assertions.assertNull(result[0]);
	}

	/**
	 * Test the case "level 2a" for the correct solution without having teethCounts specified.
	 */
	@Test
	void testGears7() {

		Fraction[] transmissionRatios = {
				new Fraction(10, 10),
				new Fraction(12, 10),
				new Fraction(14, 10)
		};

		Fraction[][] result = Gears.findToothcounts(transmissionRatios);

		// check that a unique solution was found
		Assertions.assertNotNull(result[0]);

		// check that the unique solution has the same ratios as the request
		for (int idxRatio = 0; idxRatio < transmissionRatios.length; ++idxRatio) {
			Assertions.assertEquals(0, transmissionRatios[idxRatio].compareTo(result[0][idxRatio]));
		}
	}

}
