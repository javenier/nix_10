package ua.com.alevel.level2.task1;

import java.util.Stack;

public class NestedParentheses {

    private static Stack<Character> stack = new Stack<>();

    public static boolean isValidNestedParentheses(String src) {
        if (src.isEmpty())
            return true;
        for (int i = 0; i < src.length(); i++) {
            if (isParentheses(src.charAt(i))) {
                if (isOpenParentheses(src.charAt(i))) {
                    stack.push(src.charAt(i));
                } else {
                    if (stack.isEmpty())
                        return false;
                    else if (!isOppositeParentheses(stack.pop(), src.charAt(i)))
                        return false;
                }
            }
        }
        if (!stack.isEmpty())
            return false;
        return true;
    }

    private static boolean isParentheses(char c) {
        if (c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']')
            return true;
        return false;
    }

    private static boolean isOpenParentheses(char c) {
        if (c == '(' || c == '{' || c == '[')
            return true;
        return false;
    }

    private static boolean isOppositeParentheses(char first, char second) {
        if (first == '(') {
            if (second == ')') {
                return true;
            }
        } else {
            if ((int) (first + 2) == 93 || (int) (first + 2) == 125)
                return true;
        }
        return false;
    }
}
