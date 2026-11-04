/*
 * @test
 * @summary Tests for java.util.Map methods, focusing on exceptional paths.
 * @run main MapWitupTest
 */

import java.util.*;
import java.util.function.*;

public class MapWitupTest {

    public static void main(String[] args) {
        testComparingByKeyWithNull();
        testComparingByKeyWithValidComparator();
    }

    public static void testComparingByKeyWithNull() {
        try {
            Comparator<Map.Entry<?, ?>> result = Map.comparingByKey(null);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Expected NullPointerException not thrown in testComparingByKeyWithNull");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }

    public static void testComparingByKeyWithValidComparator() {
        Comparator<Integer> cmp = Integer::compare;
        Comparator<Map.Entry<Integer, String>> comp = Map.comparingByKey(cmp);
        Map.Entry<Integer, String> e1 = new AbstractMap.SimpleEntry<>(1, "a");
        Map.Entry<Integer, String> e2 = new AbstractMap.SimpleEntry<>(2, "b");
        int comparison = comp.compare(e1, e2);
        if (comparison >= 0) {
            System.err.println("Comparison did not produce expected result in testComparingByKeyWithValidComparator");
            System.exit(1);
        }
    }
}
