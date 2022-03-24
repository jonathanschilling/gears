package de.labathome.gears;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.math3.fraction.Fraction;

public class Gears {

	public static final int MIN_TOOTH_SUM_DEFAULT = 2;
	public static final int MAX_TOOTH_SUM_DEFAULT = 200;

	protected static final Logger logger = Logger.getLogger(Gears.class.getName());

	public static Fraction[] findToothcounts(Fraction[] transmissionRatios) {
		return findToothcounts(transmissionRatios, MAX_TOOTH_SUM_DEFAULT, MIN_TOOTH_SUM_DEFAULT, null);
	}

	public static Fraction[] findToothcounts(Fraction[] transmissionRatios, int maxToothSum, int minToothSum, List<int[][]> teethCounts1) {

		Fraction[] uniqueSolution = null;

		for (int toothSum = minToothSum; toothSum <= maxToothSum; ++toothSum) {
			logger.finer(String.format("tooth sum: %d", toothSum));

			boolean allMatch = true;

			Fraction[] trialSolution = new Fraction[transmissionRatios.length];

			final int[][] trialTeethCounts1;
			if (teethCounts1 != null) {
				trialTeethCounts1 = new int[transmissionRatios.length][2];
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
							trialTeethCounts1[idxRatio][0] = teeth1;
							trialTeethCounts1[idxRatio][1] = teeth2;
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
			logger.warning("No possible combination was found.");
			return null;
		}
	}
}
