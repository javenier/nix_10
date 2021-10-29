package ua.com.alevel.level1.task1;

import java.util.HashSet;

public class UniqueSymbols {

    public static int countUniqueSymbols(String src) {
        String[] uncheckedArr = src.split(" ");
        HashSet<String> numbers = new HashSet<>();
        for (String s : uncheckedArr) {
            if (Character.isDigit(s.charAt(0)))
                numbers.add(s);
        }
        return numbers.size();
    }
}
