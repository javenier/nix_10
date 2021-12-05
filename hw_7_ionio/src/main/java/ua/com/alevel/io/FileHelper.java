package ua.com.alevel.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static void write(List<String[]> src, boolean append, String path) {
        File file = new File(path);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
            for (int j = 0; j < src.size(); j++) {
                String res = "";
                String[] arr = src.get(j);
                for (int i = 0; i < arr.length; i++) {
                    res += arr[i];
                    if (i != arr.length - 1)
                        res += ",";
                }
                writer.write(res);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String[]> read(String path) {
        List<String[]> output = new ArrayList<>();
        File file = new File(path);
        BufferedReader reader = null;
        String line;
        if (file != null) {
            try {
                reader = new BufferedReader(new FileReader(file));
                while (reader.ready()) {
                    line = reader.readLine();
                    String[] arr = line.split(",");
                    output.add(arr);
                }
                reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return output;
    }
}