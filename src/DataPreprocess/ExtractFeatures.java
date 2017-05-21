package DataPreprocess;

/**
 * Created by Z003R98D on 3/15/2017.
 */
public class ExtractFeatures {

    // 首字母是否大写
    public static boolean isInitCap(String s) {
        char[] chs = s.toCharArray();
        if (chs[0] >= 'A' && chs[0] <= 'Z') return true;
        return false;
    }

    // 是否所有字母大写
    public static boolean isAllCaps(String s) {
        char[] chs = s.toCharArray();
        for (char ch : chs) {
            if (ch < 'A' || ch > 'Z')
                return false;
        }
        return true;
    }

    // 是否至少包含一个数字
    public static boolean isContainsDigits(String s) {
        char[] chs = s.toCharArray();
        for (char ch : chs) {
            if (ch >= '0' && ch <= '9')
                return true;
        }
        return false;
    }

    // 是否所有字符都是数字
    public static boolean isAllDigits(String s) {
        char[] chs = s.toCharArray();
        for (char ch : chs) {
            if (ch < '0' || ch > '9')
                return false;
        }
        return true;
    }

    // 是否包含至少一个点（.）
    public static boolean isContainsDots(String s) {
        return s.contains(".");
    }

    // 是否包含至少一个 -
    public static boolean isContainsDash(String s) {
        return s.contains("-");
    }

    // 是否是单字母
    public static boolean isSingleChar(String s) {
        char[] chs = s.toCharArray();
        if (chs.length == 1 && ((chs[0] >= 'A' && chs[0] <= 'Z') || (chs[0] >= 'a' && chs[0] <= 'z')))
            return true;
        return false;
    }

    // 是否一个大写字母
    public static boolean isCapLetter(String s) {
        char[] chs = s.toCharArray();
        if (chs.length == 0 && (chs[0] >= 'A' && chs[0] <= 'Z'))
            return true;
        return false;
    }

    // 是否包含括号
    public static boolean isContainBracket(String s) {
        char[] chs = s.toCharArray();
        for (char ch : chs) {
            if (ch == '(' || ch == ')')
                return true;
        }
        return false;
    }

    // 是否包含逗号
    public static boolean isContainComma(String s) {
        return s.contains(",");
    }

    public static String GetFeatures(String s) {
        System.out.println("Extracting features of " + s);
        String features = "";
        if (isInitCap(s)) features += "INITCAPS ";
        if (isAllCaps(s)) features += "ALLCAPS ";
        if (isContainsDigits(s)) features += "CONTAINSDIGITS ";
        if (isAllDigits(s)) features += "ALLDIGITS ";
        if (isContainsDots(s)) features += "CONTAINSDOTS ";
        if (isContainsDash(s)) features += "CONTAINSDASH ";
        if (isSingleChar(s)) features += "SINGLECHAR ";
        if (isCapLetter(s)) features += "CAPLETTER ";
        if (isContainBracket(s)) features += "CONTAINSBRACKET ";
        if (isContainComma(s)) features += "CONTAINSCOMMA ";
        return features.trim();
    }
}
