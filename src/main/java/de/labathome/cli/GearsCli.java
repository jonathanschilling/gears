package de.labathome.cli;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.concurrent.Callable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import de.labathome.gears.Gears;
import de.labathome.gears.GearsInput;
import de.labathome.gears.GearsOutput;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "gears", version = "gears 1.0.0", description = "Gearbox Calculator")
public class GearsCli implements Callable<Integer> {

	@Parameters(index = "0", description = "*.json input file")
	private String filename;

	@Override
	public Integer call() throws Exception {
		try {
			// check if input file exists
			File inputFile = new File(filename);
			if (!inputFile.exists()) {
				System.out.printf("input file '%s' not found\n", filename);
			}

			// load JSON input from input file
			Reader reader = new FileReader(inputFile);
			JsonObject inputObj = new Gson().fromJson(reader, JsonObject.class);
			GearsInput input = GearsInput.fromJson(inputObj);

			GearsOutput output = Gears.findToothcounts(input);

			String inputFileName = inputFile.getName();
			String inputFilePrefix = inputFileName.substring(0, inputFileName.indexOf(".json"));
			String outputFilename = inputFilePrefix + "_out.json";

			File outputFile = new File(outputFilename);
			try (FileWriter writer = new FileWriter(outputFile)) {
				writer.write(output.toJson());
			}
		} catch (Exception e) {
            e.printStackTrace();
        }
		return 0;
	}

	public static void main(String[] args) {
    	int exitCode = new CommandLine(new GearsCli()).execute(args);
        System.exit(exitCode);
	}
}
