/* @test    @summary Test for Map.comparingByValue method to ensure correct comparator behavior    @run main MapWitupTest */

import java.util.*;
import java.util.function.*;

public class MapWitupTest {

    public static void main(String[] args) {
        testComparingByValueWithNullComparator();
        testComparingByValueWithCustomComparator();
    }

    private static void testComparingByValueWithNullComparator() {
        Map.Entry<String, Integer> entry1 = new AbstractMap.SimpleEntry<>("a", 1);
        Map.Entry<String, Integer> entry2 = new AbstractMap.SimpleEntry<>("b", 2);
        Comparator<Map.Entry<String, Integer>> comparator = Map.comparingByValue(null);
        int result = comparator.compare(entry1, entry2);
        if (result >= 0) {
            throw new AssertionError("Expected negative result when comparing 1 and 2, but got: " + result);
        }
    }

    private static void testComparingByValueWithCustomComparator() {
        Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("x", "apple");
        Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("y", "banana");
        Comparator<String> stringLengthComparator = (s1, s2) -> Integer.compare(s1.length(), s2.length());
        Comparator<Map.Entry<String, String>> comparator = Map.comparingByValue(stringLengthComparator);
        int compareResult = comparator.compare(entry1, entry2);
        if (compareResult != 0) {
            throw new AssertionError("Expected comparison result 0 for equal length strings, but got: " + compareResult);
        }
        Map.Entry<String, String> entry3 = new AbstractMap.SimpleEntry<>("z", "kiwi");
        int result = comparator.compare(entry1, entry3);
        if (result != 0) {
            throw new AssertionError("Expected comparison result 0 for equal length strings, but got: " + result);
        }
    }
}
