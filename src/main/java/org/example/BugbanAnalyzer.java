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
        + "\noutput for problems only in second file and"
        + "output for problems in both files:");

    String firstInputFile = scanner.next();
    String secondInputFile = scanner.next();
    String outputFileOnlyFirst = scanner.next();
    String outputFileOnlySecond = scanner.next();
    String outputFileBoth = scanner.next();

    try {
      List<JsonObject> firstProblems = readBugbanOutput(firstInputFile);
      List<JsonObject> secondProblems = readBugbanOutput(secondInputFile);

      Set<JsonObject> onlyInFirst = new HashSet<>(firstProblems);
      onlyInFirst.removeAll(secondProblems);

      Set<JsonObject> onlyInSecond = new HashSet<>(secondProblems);
      onlyInSecond.removeAll(firstProblems);

      Set<JsonObject> inBoth = new HashSet<>(firstProblems);
      inBoth.retainAll(secondProblems);

      writeOutputFile(outputFileOnlyFirst, onlyInFirst);
      writeOutputFile(outputFileOnlySecond, onlyInSecond);
      writeOutputFile(outputFileBoth, inBoth);

      System.out.println("Problems were successfully written to the specified output files.");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static List<JsonObject> readBugbanOutput(String inputFile) throws IOException {
    JsonArray problemsArray = JsonParser.parseReader(new FileReader(inputFile)).getAsJsonObject().getAsJsonArray("problems");
    List<JsonObject> problems = new ArrayList<>();
    for (int i = 0; i < problemsArray.size(); i++) {
      problems.add(problemsArray.get(i).getAsJsonObject());
    }
    return problems;
  }

  private static void writeOutputFile(String outputFile, Set<JsonObject> problems) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonObject output = new JsonObject();
    JsonArray problemsArray = new JsonArray();
    for (JsonObject problem : problems) {
      problemsArray.add(problem);
    }
    output.add("problems", problemsArray);
    try (FileWriter writer = new FileWriter(outputFile)) {
      gson.toJson(output, writer);
    }
  }
}