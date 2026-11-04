/*
 * @test
 * @summary Tests for java.util.Map methods based on WIT analysis
 * @run main MapWitupTest
 */

import java.util.*;
import java.util.function.*;

public class MapWitupTest {

    public static void main(String[] args) {
        testComparingByValueWithNullComparator();
        testComparingByValueWithValidComparator();
    }

    private static void testComparingByValueWithNullComparator() {
        try {
            Comparator<Map.Entry<String, String>> result = Map.comparingByValue(null);
            // If no exception, test fails
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null comparator");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }

    private static void testComparingByValueWithValidComparator() {
        Comparator<String> cmp = String::compareTo;
        Comparator<Map.Entry<String, String>> comp = Map.comparingByValue(cmp);
        Map.Entry<String, String> e1 = new AbstractMap.SimpleEntry<>("a", "apple");
        Map.Entry<String, String> e2 = new AbstractMap.SimpleEntry<>("b", "banana");
        int comparison = comp.compare(e1, e2);
        if (comparison >= 0) {
            System.err.println("Comparison did not produce expected negative result");
            System.exit(1);
        } else {
            System.out.println("Comparison by value works as expected");
        }
    }
}
