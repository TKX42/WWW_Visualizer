package util;

public class UrlEditor {
    static int countMatches(String input, String search) {
        int index = input.indexOf(search);
        int count = 0;
        while (index != -1) {
            count++;
            input = input.substring(index + 1);
            index = input.indexOf(search);
        }

        return count;
    }

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

    public static String getCutUrl(String url) {
        if(countMatches(url, "/") >= 3) {
            return url.substring(0, ordinalIndexOf(url, "/", 3));
        }
        else {
            return url.substring(0, url.length());
        }
    }

    public static String getBaseUrl(String url) {
        int endOfBaseUrl = url.length();

        if (countMatches(url, "/") >= 3) {
            endOfBaseUrl = ordinalIndexOf(url, "/", 3);
        }

        if (url.contains("www")) {
            return url.substring(ordinalIndexOf(url, ".", 1) + 1, endOfBaseUrl);
        }
        return url.substring(ordinalIndexOf(url, "/", 2) + 1, endOfBaseUrl);
    }
}
