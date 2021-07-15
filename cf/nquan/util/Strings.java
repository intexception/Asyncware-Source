package cf.nquan.util;

public class Strings {
    public static String capitalizeOnlyFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1, string.length()).toLowerCase();
    }

    public static String upperCaseFirst(String value) {

        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }

}
