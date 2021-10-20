package ua.com.alevel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class SortedEntries {

    public static void countFrequencyOfChar(String str) {
        ArrayList<Character> characters = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i)))
                characters.add(str.charAt(i));
        }
        Collections.sort(characters);
        Set<Character> set = new HashSet<Character>(characters);
        for (Character c : set)
            System.out.println(c + ": " + Collections.frequency(characters, c));
    }
}
