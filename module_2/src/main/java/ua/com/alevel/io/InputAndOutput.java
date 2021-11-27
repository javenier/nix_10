package ua.com.alevel.io;

import java.io.*;

public class InputAndOutput {

    public static String getInputFromFile(String path) throws FileNotFoundException {
        File file = new File(path);
        String input = "";
        BufferedReader reader = null;
        if (file != null) {
            try {
                reader = new BufferedReader(new FileReader(file));
                while (reader.ready()) {
                    input += reader.readLine();
                    input += "\n";
                }
                reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }

    public static void writeResultToFile(String path, String result) {
        File file = new File(path);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
