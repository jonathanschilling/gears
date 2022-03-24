package de.labathome.gears;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math3.fraction.Fraction;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

class DemoGears {

	public static void main(String[] args) {
		Logger.getGlobal().setLevel(Level.WARNING);

		System.out.println("demoDirectCall:");
		demoDirectCall();

		System.out.println("");
		System.out.println("demoJsonInputCall:");
		demoJsonInputCall();
	}

	static void demoJsonInputCall() {

		String resourceName = "/demoInput.json";
		InputStream inputStream = DemoGears.class.getResourceAsStream(resourceName);
		if (inputStream == null) {
			throw new RuntimeException("Cannot find resource '" + resourceName + "'");
		}

		try (Reader reader = new InputStreamReader(inputStream, "UTF-8")) {
			JsonObject inputObj = new Gson().fromJson(reader, JsonObject.class);

			GearsInput input = GearsInput.fromJson(inputObj);
			GearsOutput output = Gears.findToothcounts(input);


			printSolution(output.solution, output.teethCounts);
			System.out.println(output.toJson());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void demoDirectCall() {

		// level 1
//		Fraction[] transmissionRatios = {
//				new Fraction(1, 2),
//				new Fraction(1, 1),
//				new Fraction(5, 2)
//		};

//		// level 2a
//		Fraction[] transmissionRatios = {
//				new Fraction(10, 10),
//				new Fraction(12, 10),
//				new Fraction(14, 10)
//		};

		//	level 2b
//		Fraction[] transmissionRatios = {
//				new Fraction(16, 10),
//				new Fraction(18, 10),
//				new Fraction(20, 10)
//		};

//		// left column
//		Fraction[] transmissionRatios = {
//			new Fraction(10, 14),
//			new Fraction(14, 14),
//			new Fraction(18, 14)
//		};

		// right column
		Fraction[] transmissionRatios = {
			new Fraction(12, 12),
			new Fraction(16, 12),
			new Fraction(20, 12)
		};

		int maxTeethSum = 100;
		int minTeethSum = 10;
		List<int[][]> teethCounts = new LinkedList<>();
		Fraction[] solution = Gears.findToothcounts(transmissionRatios, maxTeethSum, minTeethSum, teethCounts);

		printSolution(solution, teethCounts);
	}

	static void printSolution(Fraction[] solution, List<int[][]> teethCounts) {
		if (solution != null) {
			System.out.println("found a unique solution:");
			for (int idxRatio = 0; idxRatio < solution.length; ++idxRatio) {
				System.out.printf("  ratio %d: %d:%d\n", idxRatio + 1,
						solution[idxRatio].getNumerator(),
						solution[idxRatio].getDenominator());
			}

			if (teethCounts != null) {
				System.out.println("possible combinations for given tooth sum range:");
				for (int[][] teethCount: teethCounts) {
					System.out.printf("tooth sum %d:\n", teethCount[0][0] + teethCount[0][1]);
					for (int idxRatio = 0; idxRatio < teethCount.length; ++idxRatio) {
						System.out.printf("  ratio %d: %d:%d\n", idxRatio, teethCount[idxRatio][0], teethCount[idxRatio][1]);
					}
				}
			}
		} else {
			System.out.println("no solution was found");
		}
	}
}
