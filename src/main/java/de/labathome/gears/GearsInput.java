package de.labathome.gears;

import org.apache.commons.math3.fraction.Fraction;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GearsInput {

	Fraction[] transmissionRatios;
	int maxToothSum = Gears.MAX_TOOTH_SUM_DEFAULT;
	int minToothSum = Gears.MIN_TOOTH_SUM_DEFAULT;
	Fraction minModule = Gears.MIN_MODULE_DEFAULT;
	Fraction maxModule = Gears.MAX_MODULE_DEFAULT;
	boolean outputTeethCounts = false;

	public GearsInput(Fraction[] transmissionRatios) {
		this.transmissionRatios = transmissionRatios;
	}

	public void setMinToothSum(int minToothSum) {
		this.minToothSum = minToothSum;
	}

	public void setMaxToothSum(int maxToothSum) {
		this.maxToothSum = maxToothSum;
	}

	public void setMinModule(Fraction minModule) {
		this.minModule = minModule;
	}

	public void setMaxModule(Fraction maxModule) {
		this.maxModule = maxModule;
	}

	public void setOutputTeethCounts(boolean outputTeethCounts) {
		this.outputTeethCounts = outputTeethCounts;
	}

	public static GearsInput fromJson(JsonObject json) {

		// required input: transmissionRatios
		JsonArray trArr = json.getAsJsonArray("transmissionRatios");
		int numRatios = trArr.size();

		if (numRatios < 1) {
			throw new RuntimeException(String.format("Must specify at least one ratio, but got %d ratios.", numRatios));
		}

		Fraction[] transmissionRatios = new Fraction[numRatios];

		for (int idxRatio=0; idxRatio<numRatios; ++idxRatio) {
			JsonArray ratioArr = trArr.get(idxRatio).getAsJsonArray();
			if (ratioArr.size() != 2) {
				throw new RuntimeException(String.format("Each ratio must consist of two elements (numerator and denominator), but got %d elements.", ratioArr.size()));
			}
			int numerator   = ratioArr.get(0).getAsInt();
			int denominator = ratioArr.get(1).getAsInt();
			transmissionRatios[idxRatio] = new Fraction(numerator, denominator);
		}

		GearsInput input = new GearsInput(transmissionRatios);

		// optional inputs: minToothSum, maxToothSum, outputTeethCounts
		JsonElement minToothSumElement = json.get("minToothSum");
		if (minToothSumElement != null) {
			input.setMinToothSum(minToothSumElement.getAsInt());
		}

		JsonElement maxToothSumElement = json.get("maxToothSum");
		if (maxToothSumElement != null) {
			input.setMaxToothSum(maxToothSumElement.getAsInt());
		}

		// TODO: minModule
		// TODO: maxModule

		JsonElement outputTeethCountsElement = json.get("outputTeethCounts");
		if (outputTeethCountsElement != null) {
			input.setOutputTeethCounts(outputTeethCountsElement.getAsBoolean());
		}

		return input;
	}

}
