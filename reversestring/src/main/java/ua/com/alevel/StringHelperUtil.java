package ua.com.alevel;

public final class StringHelperUtil {

    private StringHelperUtil() { }

    public static String reverse(String text, boolean fullString){
        String result = "";
        if(fullString){
            for(int i = text.length() - 1; i >= 0; i--)
                result += text.charAt(i);
        }
        else{
            String[] words = text.split(" ");
            for(int i = 0; i < words.length; i++){
                for(int j = words[i].length() - 1; j >= 0; j--){
                    result += words[i].charAt(j);
                }
                result += " ";
            }
        }
        return result;
    }

    public static String reverse(String text, String dest){
        int pos = text.indexOf(dest);
        String reversedDest = "";
        for(int i = dest.length() - 1; i >= 0; i--)
            reversedDest += dest.charAt(i);
        String result = text.replaceAll(dest, reversedDest);
        return result;
    }

    public static String reverse(String text, int firstIndex, int lastIndex){
        String dest = text.substring(firstIndex, lastIndex);
        String reversedDest = "";
        for(int i = dest.length() - 1; i >= 0; i--)
            reversedDest += dest.charAt(i);
        String result = text.replaceAll(dest, reversedDest);
        return result;
    }
}
