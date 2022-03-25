package de.labathome.gears;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.math3.fraction.Fraction;

public class Gears {

	public static final int MIN_TOOTH_SUM_DEFAULT = 2;
	public static final int MAX_TOOTH_SUM_DEFAULT = 200;

	public static final Fraction MIN_MODULE_DEFAULT = new Fraction(1, 1);
	public static final Fraction MAX_MODULE_DEFAULT = new Fraction(1, 1);

	/** DIN 780, Reihe 1 */
	public static final Fraction[] NORM_MODULES = {
			new Fraction(  5, 100), //  0.05   0
			new Fraction(  6, 100), //  0.06   1
			new Fraction(  8, 100), //  0.08   2
			new Fraction(  1,  10), //  0.1    3
			new Fraction( 12, 100), //  0.12   4
			new Fraction( 16, 100), //  0.16   5
			new Fraction(  2,  10), //  0.2    6
			new Fraction( 25, 100), //  0.25   7
			new Fraction(  3,  10), //  0.3    8
			new Fraction(  4,  10), //  0.4    9
			new Fraction(  5,  10), //  0.5   10
			new Fraction(  6,  10), //  0.6   11
			new Fraction(  7,  10), //  0.7   12
			new Fraction(  8,  10), //  0.8   13
			new Fraction(  9,  10), //  0.9   14
			new Fraction(  1,   1), //  1.0   15
			new Fraction(125, 100), //  1.25  16
			new Fraction( 15,  10), //  1.5   17
			new Fraction(  2,   1), //  2.0   18
			new Fraction(  5,   2), //  2.5   19
			new Fraction(  3,   1), //  3.0   20
			new Fraction(  4,   1), //  4.0   21
			new Fraction(  5,   1), //  5.0   22
			new Fraction(  6,   1), //  6.0   23
			new Fraction(  8,   1), //  8.0   24
			new Fraction( 10,   1), // 10.0   25
			new Fraction( 12,   1), // 12.0   26
			new Fraction( 16,   1), // 16.0   27
			new Fraction( 20,   1), // 20.0   28
			new Fraction( 25,   1), // 25.0   29
			new Fraction( 32,   1), // 32.0   30
			new Fraction( 40,   1), // 40.0   31
			new Fraction( 50,   1)  // 50.0   32
	};

	protected static final Logger logger = Logger.getLogger(Gears.class.getName());

	public static Fraction[][] findToothcounts(Fraction[] transmissionRatios) {
		return findToothcounts(transmissionRatios, MIN_TOOTH_SUM_DEFAULT, MAX_TOOTH_SUM_DEFAULT, MIN_MODULE_DEFAULT, MAX_MODULE_DEFAULT, null);
	}

	public static Fraction[][] findToothcounts(Fraction[] transmissionRatios,
			int minToothSum, int maxToothSum,
			Fraction minModule, Fraction maxModule, List<int[][]> teethCounts) {

		// TODO: revert 2d output
		// --> list of modules has to go with teethCounts

		Fraction[] uniqueSolution = null;
		Fraction[] uniqueModules  = null;

		for (int toothSum = minToothSum; toothSum <= maxToothSum; ++toothSum) {
			logger.finer(String.format("tooth sum: %d", toothSum));

			boolean allMatch = true;

			Fraction[] trialSolution = new Fraction[transmissionRatios.length];
			Fraction[] trialModules  = new Fraction[transmissionRatios.length];

			final int[][] trialTeethCounts;
			if (teethCounts != null) {
				trialTeethCounts = new int[transmissionRatios.length][2];
			} else {
				trialTeethCounts = null;
			}

			for (int idxRatio = 0; idxRatio < transmissionRatios.length && allMatch; ++idxRatio) {

				boolean foundOne = false;

				for (int idxModule = 0; idxModule < NORM_MODULES.length && !foundOne; ++idxModule) {
					Fraction module = NORM_MODULES[idxModule];

					if (minModule.compareTo(module) <= 0 && maxModule.compareTo(module) >=0) {
						//System.out.println("consider module " + module);

						Fraction minToothSumTimesModule = module.multiply(minToothSum-1);
						int loopStart = (int) Math.ceil(minToothSumTimesModule.doubleValue());

						Fraction maxToothSumTimesModule = module.multiply(toothSum);
						int loopEnd = (int) Math.ceil(maxToothSumTimesModule.doubleValue());

						//for (int teeth1 = minToothSum-1; teeth1 < toothSum && !foundOne; ++teeth1) {
						for (int teeth1 = loopStart; teeth1 < loopEnd && !foundOne; ++teeth1) {
							//int teeth2 = toothSum - teeth1;
							int teeth2 = loopEnd - teeth1;

							//logger.finer(String.format("testing ratio %d:%d", teeth1, teeth2));
							//System.out.printf("testing ratio %d:%d\n", teeth1, teeth2);

							Fraction currentRatio = new Fraction(teeth1, teeth2);
							if (transmissionRatios[idxRatio].compareTo(currentRatio) == 0) {
								foundOne = true;
								trialSolution[idxRatio] = currentRatio;
								trialModules[idxRatio]  = module;

								if (trialTeethCounts != null) {
									trialTeethCounts[idxRatio][0] = teeth1;
									trialTeethCounts[idxRatio][1] = teeth2;
								}
							}
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
					infoString += String.format("  ratio %d (module %.2f): %d:%d", idxRatio + 1,
							trialModules[idxRatio].doubleValue(),
							trialSolution[idxRatio].getNumerator(), trialSolution[idxRatio].getDenominator());
					if (idxRatio < trialSolution.length-1) {
						infoString += "\n";
					}
				}
				logger.fine(infoString);

				if (teethCounts != null) {
					if (uniqueSolution == null) {
						uniqueSolution = trialSolution;
						uniqueModules = trialModules;
					}

					teethCounts.add(trialTeethCounts);
				} else {
					return new Fraction[][] {trialSolution, trialModules};
				}
			}
		}

		if (teethCounts != null && teethCounts.size() > 0) {
			return new Fraction[][] {uniqueSolution, uniqueModules};
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

		Fraction[][] solution = Gears.findToothcounts(input.transmissionRatios,
				input.minToothSum, input.maxToothSum,
				input.minModule, input.maxModule,
				teethCounts);

		return new GearsOutput(solution[0], solution[1], teethCounts);
	}
}
