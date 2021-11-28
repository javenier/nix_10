package ua.com.alevel.names;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniqueNameFinder {

    private static final String REGEX = "[a-zA-Z]+";

    public static String findFirstUniqueName(String input) {
        List<String> names = getNames(input);
        Map<String, Integer> map = countFrequencyOfEntries(names);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1)
                return entry.getKey();
        }
        return null;
    }

    private static Map<String, Integer> countFrequencyOfEntries(List<String> names) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (String name : names) {
            if (!map.containsKey(name))
                map.put(name, Collections.frequency(names, name));
        }
        return map;
    }

    private static List<String> getNames(String input) {
        List<String> names = new ArrayList<>();
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            names.add(input.substring(matcher.start(), matcher.end()));
        }
        return names;
    }
}