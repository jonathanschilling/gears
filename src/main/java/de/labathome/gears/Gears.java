package de.labathome.gears;

import java.util.LinkedList;
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

	public static Fraction[] findToothcounts(Fraction[] transmissionRatios, int maxToothSum, int minToothSum, List<int[][]> teethCounts) {

		Fraction[] uniqueSolution = null;

		for (int toothSum = minToothSum; toothSum <= maxToothSum; ++toothSum) {
			logger.finer(String.format("tooth sum: %d", toothSum));

			boolean allMatch = true;

			Fraction[] trialSolution = new Fraction[transmissionRatios.length];

			final int[][] trialTeethCounts;
			if (teethCounts != null) {
				trialTeethCounts = new int[transmissionRatios.length][2];
			} else {
				trialTeethCounts = null;
			}

			for (int idxRatio = 0; idxRatio < transmissionRatios.length && allMatch; ++idxRatio) {

				boolean foundOne = false;

				for (int teeth1 = minToothSum-1; teeth1 < toothSum && !foundOne; ++teeth1) {
					int teeth2 = toothSum - teeth1;

					logger.finer(String.format("testing ratio %d:%d", teeth1, teeth2));

					Fraction currentRatio = new Fraction(teeth1, teeth2);
					if (transmissionRatios[idxRatio].compareTo(currentRatio) == 0) {
						foundOne = true;
						trialSolution[idxRatio] = currentRatio;

						if (trialTeethCounts != null) {
							trialTeethCounts[idxRatio][0] = teeth1;
							trialTeethCounts[idxRatio][1] = teeth2;
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

				if (teethCounts != null) {
					if (uniqueSolution == null) {
						uniqueSolution = trialSolution;
					}

					teethCounts.add(trialTeethCounts);
				} else {
					return trialSolution;
				}
			}
		}

		if (teethCounts != null && teethCounts.size() > 0) {
			return uniqueSolution;
		} else {
			logger.warning("No possible combination was found.");
			return null;
		}
	}

	public static GearsOutput findToothcounts(GearsInput input) {

		final List<int[][]> teethCounts;
		if (input.outputTeethCounts) {
			teethCounts = new LinkedList<>();
		} else {
			teethCounts = null;
		}

		Fraction[] solution = Gears.findToothcounts(input.transmissionRatios, input.maxToothSum, input.minToothSum, teethCounts);

		return new GearsOutput(solution, teethCounts);
	}
}
