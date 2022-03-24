package de.labathome.gears;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math3.fraction.Fraction;

public class Gears {

	public static final int MIN_TOOTH_SUM_DEFAULT = 2;
	public static final int MAX_TOOTH_SUM_DEFAULT = 200;

	protected static final Logger logger = Logger.getLogger(Gears.class.getName());

	public static Fraction[] findToothcounts(Fraction[] transmissionRatios) {
		return findToothcounts(transmissionRatios, MAX_TOOTH_SUM_DEFAULT, MIN_TOOTH_SUM_DEFAULT, null);
	}

	public static Fraction[] findToothcounts(Fraction[] transmissionRatios, int maxToothSum) {
		return findToothcounts(transmissionRatios, maxToothSum, MIN_TOOTH_SUM_DEFAULT, null);
	}

	public static Fraction[] findToothcounts(Fraction[] transmissionRatios, int maxToothSum, int minToothSum) {
		return findToothcounts(transmissionRatios, maxToothSum, minToothSum, null);
	}

	public static Fraction[] findToothcounts(Fraction[] transmissionRatios, int maxToothSum, int minToothSum, List<int[]> teethCounts1) {

		Fraction[] uniqueSolution = null;

		for (int toothSum = minToothSum; toothSum <= maxToothSum; ++toothSum) {
			logger.finer(String.format("tooth sum: %d", toothSum));

			boolean allMatch = true;

			Fraction[] trialSolution = new Fraction[transmissionRatios.length];

			final int[] trialTeethCounts1;
			if (teethCounts1 != null) {
				trialTeethCounts1 = new int[transmissionRatios.length];
			} else {
				trialTeethCounts1 = null;
			}

			for (int idxRatio = 0; idxRatio < transmissionRatios.length && allMatch; ++idxRatio) {

				boolean foundOne = false;

				for (int teeth1 = 1; teeth1 < toothSum - 1 && !foundOne; ++teeth1) {
					int teeth2 = toothSum - teeth1;

					logger.finer(String.format("testing ratio %d:%d", teeth1, teeth2));

					Fraction currentRatio = new Fraction(teeth1, teeth2);
					if (transmissionRatios[idxRatio].compareTo(currentRatio) == 0) {
						foundOne = true;
						trialSolution[idxRatio] = currentRatio;

						if (trialTeethCounts1 != null) {
							trialTeethCounts1[idxRatio] = teeth1;
						}
					}
				}

				if (!foundOne) {
					allMatch = false;
				}
			}

			if (allMatch) {
				String infoString = "";
				infoString += String.format("found a solution for tooth sum %d:\n", toothSum);
				for (int idxRatio = 0; idxRatio < trialSolution.length; ++idxRatio) {
					infoString += String.format("  ratio %d: %d:%d", idxRatio + 1,
							trialSolution[idxRatio].getNumerator(), trialSolution[idxRatio].getDenominator());
					if (idxRatio < trialSolution.length-1) {
						infoString += "\n";
					}
				}
				logger.fine(infoString);

				if (teethCounts1 != null) {
					if (uniqueSolution == null) {
						uniqueSolution = trialSolution;
					}

					teethCounts1.add(trialTeethCounts1);
				} else {
					return trialSolution;
				}
			}
		}

		if (teethCounts1 != null && teethCounts1.size() > 0) {
			return uniqueSolution;
		} else {
			logger.warning("No possible combination found.");
			return null;
		}
	}

	public static void main(String[] args) {
		Logger.getGlobal().setLevel(Level.WARNING);

		int minToothSum = MIN_TOOTH_SUM_DEFAULT;
		int maxToothSum = MAX_TOOTH_SUM_DEFAULT;

//		// Stufe 1
//		Fraction[] transmissionRatios = {
//				new Fraction(1, 2),
//				new Fraction(1, 1),
//				new Fraction(5, 2)
//		};

//		// Stufe 2a
//		Fraction[] transmissionRatios = {
//				new Fraction(10, 10),
//				new Fraction(12, 10),
//				new Fraction(14, 10)
//		};

//		//	Stufe 2b
//		Fraction[] transmissionRatios = {
////			new Fraction(16, 10),
//				new Fraction(18, 10),
//				new Fraction(20, 10)
//		};

		// links
		Fraction[] transmissionRatios = {
			new Fraction(10, 14),
			new Fraction(14, 14),
			new Fraction(18, 14)
		};

//		// rechts
//		Fraction[] transmissionRatios = {
//			new Fraction(12, 12),
//			new Fraction(16, 12),
//			new Fraction(20, 12)
//		};

		List<int[]> teethCounts1 = new LinkedList<>();
		Fraction[] result = Gears.findToothcounts(transmissionRatios, maxToothSum, minToothSum, teethCounts1);

		if (result != null) {
			System.out.println("found a unique solution:");
			for (int idxRatio = 0; idxRatio < result.length; ++idxRatio) {
				System.out.printf("  ratio %d: %d:%d\n", idxRatio + 1,
						result[idxRatio].getNumerator(),
						result[idxRatio].getDenominator());
			}

			System.out.println("possible combinations for given tooth sum range:");
			for (int[] teethCount1: teethCounts1) {
				int[] teethCount2 = new int[teethCount1.length];
				for (int idxRatio = 0; idxRatio < teethCount1.length; ++idxRatio) {
					int scale = teethCount1[idxRatio] / result[idxRatio].getNumerator();
					teethCount2[idxRatio] = scale * result[idxRatio].getDenominator();
				}

				System.out.printf("tooth sum %d\n", teethCount1[0] + teethCount2[0]);
				for (int idxRatio = 0; idxRatio < teethCount1.length; ++idxRatio) {
					System.out.printf("  ratio %d: %d:%d\n", idxRatio, teethCount1[idxRatio], teethCount2[idxRatio]);
				}
			}
		}
	}

}
