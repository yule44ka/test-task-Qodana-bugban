package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BugbanAnalyzer {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Specify paths for first file, second file, "
        + "output for problems only in first file, "
        + "\noutput for problems only in second file and "
        + "output for problems in both files:");

    String firstInputFile = scanner.next();
    String secondInputFile = scanner.next();
    String outputFileOnlyFirst = scanner.next();
    String outputFileOnlySecond = scanner.next();
    String outputFileBoth = scanner.next();

    try {
      List<Problem> firstProblems = readBugbanOutput(firstInputFile);
      List<Problem> secondProblems = readBugbanOutput(secondInputFile);

      Set<Problem> onlyInFirst = new HashSet<>(firstProblems);
      onlyInFirst.removeAll(secondProblems);

      Set<Problem> onlyInSecond = new HashSet<>(secondProblems);
      onlyInSecond.removeAll(firstProblems);

      Set<Problem> inBoth = new HashSet<>(firstProblems);
      inBoth.retainAll(secondProblems);

      writeOutputFile(outputFileOnlyFirst, onlyInFirst);
      writeOutputFile(outputFileOnlySecond, onlyInSecond);
      writeOutputFile(outputFileBoth, inBoth);

      System.out.println("Problems were successfully written to the specified output files.");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads Bugban output from the specified input file.
   *
   * @param inputFile path to the Bugban output file
   * @return a list of Problem objects read from the file
   * @throws IOException if an I/O error occurs while reading the file
   */
  private static List<Problem> readBugbanOutput(String inputFile) throws IOException {
    JsonArray problemsArray = JsonParser.parseReader(new FileReader(inputFile)).getAsJsonObject().getAsJsonArray("problems");
    List<Problem> problems = new ArrayList<>();
    for (int i = 0; i < problemsArray.size(); i++) {
      JsonObject problemObject = problemsArray.get(i).getAsJsonObject();
      String hash = problemObject.get("hash").getAsString();
      Set<String> data = new HashSet<>();
      JsonArray dataArray = problemObject.getAsJsonArray("data");
      for (int j = 0; j < dataArray.size(); j++) {
        data.add(dataArray.get(j).getAsString());
      }
      problems.add(new Problem(hash, data));
    }
    return problems;
  }

  /**
   * Writes the specified problems to the output file in JSON format.
   *
   * @param outputFile path to the output file
   * @param problems set of Problem objects to be written to the file
   * @throws IOException if an I/O error occurs while writing to the file
   */
  private static void writeOutputFile(String outputFile, Set<Problem> problems) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonArray problemsArray = new JsonArray();
    for (Problem problem : problems) {
      JsonObject problemObject = new JsonObject();
      problemObject.addProperty("hash", problem.getHash());
      JsonArray dataArray = new JsonArray();
      for (String data : problem.getData()) {
        dataArray.add(data);
      }
      problemObject.add("data", dataArray);
      problemsArray.add(problemObject);
    }
    JsonObject output = new JsonObject();
    output.add("problems", problemsArray);
    try (FileWriter writer = new FileWriter(outputFile)) {
      gson.toJson(output, writer);
    }
  }
}
