package ua.com.alevel;

import ua.com.alevel.dates.DateTransformation;
import ua.com.alevel.io.InputAndOutput;
import ua.com.alevel.names.UniqueNameFinder;

import java.io.FileNotFoundException;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String input = InputAndOutput.getInputFromFile("module_2/src/main/resources/task1/input.txt");
        String resultStr = "";
        Set<String> res = DateTransformation.transformInputToDates(input);
        for (String s : res) {
            resultStr = resultStr + s + " ";
        }
        InputAndOutput.writeResultToFile("module_2/src/main/resources/task1/output.txt", resultStr);

        String input2 = InputAndOutput.getInputFromFile("module_2/src/main/resources/task2/input.txt");
        InputAndOutput.writeResultToFile("module_2/src/main/resources/task2/output.txt", UniqueNameFinder.findFirstUniqueName(input2));
    }
}
