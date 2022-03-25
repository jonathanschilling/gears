package de.labathome.gears;

import java.util.List;

import org.apache.commons.math3.fraction.Fraction;

public class GearsOutput {

	protected Fraction[] solution;
	protected Fraction[] modules;
	protected List<int[][]> teethCounts;

	public GearsOutput(Fraction[] solution, Fraction[] modules, List<int[][]> teethCounts) {
		this.solution = solution;
		this.modules = modules;
		this.teethCounts = teethCounts;
	}

	public String toJson() {

		String json = "{";
		if (solution != null) {
			json += solutionToJson();
			json += ",";
			json += modulesToJson();
			if (teethCounts != null) {
				json += ",";
				json += teethCountsToJson();
			}
		}
		json += "}";

		return json;
	}

	private String solutionToJson() {
		String json = "\"solution\":[";

		for (int idxRatio = 0; idxRatio < solution.length; ++idxRatio) {
			json += "[";
			json += Integer.toString(solution[idxRatio].getNumerator()) + ",";
			json += Integer.toString(solution[idxRatio].getDenominator());
			if (idxRatio < solution.length-1) {
				json += "],";
			} else {
				json += "]";
			}
		}

		json += "]";

		return json;
	}

	private String modulesToJson() {
		String json = "\"modules\":[";

		for (int idxRatio = 0; idxRatio < modules.length; ++idxRatio) {
			json += "[";
			json += Integer.toString(modules[idxRatio].getNumerator()) + ",";
			json += Integer.toString(modules[idxRatio].getDenominator());
			if (idxRatio < modules.length-1) {
				json += "],";
			} else {
				json += "]";
			}
		}

		json += "]";

		return json;
	}

	private String teethCountsToJson() {
		String json = "\"teethCounts\":[";

		for (int i = 0; i < teethCounts.size(); ++i) {
			int[][] teethCount = teethCounts.get(i);
			json += "[";

			for (int idxRatio = 0; idxRatio < teethCount.length; ++idxRatio) {
				json += "[";
				json += Integer.toString(teethCount[idxRatio][0]) + ",";
				json += Integer.toString(teethCount[idxRatio][1]);
				if (idxRatio < teethCount.length-1) {
					json += "],";
				} else {
					json += "]";
				}
			}

			if (i < teethCounts.size()-1) {
				json += "],";
			} else {
				json += "]";
			}
		}

		json += "]";

		return json;
	}
}
