package ua.com.alevel;

import ua.com.alevel.cities.CheapestCostOfPath;
import ua.com.alevel.dates.DateTransformation;
import ua.com.alevel.io.InputAndOutput;
import ua.com.alevel.names.UniqueNameFinder;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        String inputForFirstTask = null;
        try {
            inputForFirstTask = InputAndOutput.getInputFromFile("src/main/resources/task1/input.txt");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        String resultStr = "";
        Set<String> res = DateTransformation.transformInputToDates(inputForFirstTask);
        for (String s : res) {
            resultStr = resultStr + s + " ";
        }
        InputAndOutput.writeResultToFile("src/main/resources/task1/output.txt", resultStr);

        String inputForSecondTask = null;
        try {
            inputForSecondTask = InputAndOutput.getInputFromFile("src/main/resources/task2/input.txt");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        InputAndOutput.writeResultToFile("src/main/resources/task2/output.txt", UniqueNameFinder.findFirstUniqueName(inputForSecondTask));

        String inputForThirdTask = null;
        try {
            inputForThirdTask = InputAndOutput.getInputFromFile("src/main/resources/task3/input.txt");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        List<Integer> list = CheapestCostOfPath.findCheapestCostOfPath(inputForThirdTask);
        String output = "";
        for (Integer i : list) {
            output += i;
            output += "\n";
        }
        InputAndOutput.writeResultToFile("src/main/resources/task3/output.txt", output);
        System.out.println("Check out output.txt files in resources");
    }
}