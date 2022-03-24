package de.labathome.gears;

import org.apache.commons.math3.fraction.Fraction;

public class GearboxCalculator {


	public static void main(String[] args) {

		int maxZahnsumme = 200;

		// Stufe 1
		Fraction[] ratios = {
				new Fraction(1, 2),
				new Fraction(1, 1),
				new Fraction(5, 2)
		};

//		// Stufe 2a
//		Fraction[] ratios = {
//				new Fraction(10, 10),
//				new Fraction(12, 10),
//				new Fraction(14, 10)
//		};

//		//	Stufe 2b
//		Fraction[] ratios = {
////			new Fraction(16, 10),
//				new Fraction(18, 10),
//				new Fraction(20, 10)
//		};

		for (int zahnsumme = 2; zahnsumme <= maxZahnsumme; ++zahnsumme) {
			// System.out.printf("Zahnsumme %d:\n", zahnsumme);

			boolean allMatch = true;

			int[] zaehne1 = new int[ratios.length];
			int[] zaehne2 = new int[ratios.length];

			for (int idxRatio = 0; idxRatio < ratios.length && allMatch; ++idxRatio) {

				boolean foundOne = false;

				for (int zahn1 = 1; zahn1 < zahnsumme - 1 && !foundOne; ++zahn1) {
					int zahn2 = zahnsumme - zahn1;

					// System.out.printf("teste %d:%d\n", zahn1, zahn2);

					Fraction currentRatio = new Fraction(zahn1, zahn2);
					if (ratios[idxRatio].compareTo(currentRatio) == 0) {
						foundOne = true;

						zaehne1[idxRatio] = zahn1;
						zaehne2[idxRatio] = zahn2;
					}
				}

				if (!foundOne) {
					allMatch = false;
				}
			}

			if (allMatch) {
				System.out.printf("Zahnsumme %d:\n", zahnsumme);
				for (int idxRatio = 0; idxRatio < ratios.length; ++idxRatio) {
					System.out.printf("  ratio %d: %d:%d\n", idxRatio + 1, zaehne1[idxRatio], zaehne2[idxRatio]);
				}
			}
		}
	}

}
