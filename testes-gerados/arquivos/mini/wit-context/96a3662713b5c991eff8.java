/*
 * @test
 * @summary Test java.util.ArrayList.replaceAll(UnaryOperator) for NPE and basic functionality
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullOperator();
        testReplaceAllBasicFunctionality();
    }

    private static void testReplaceAllNullOperator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testReplaceAllBasicFunctionality() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        UnaryOperator<String> toUpper = new UnaryOperator<String>() {
            public String apply(String s) {
                return s.toUpperCase();
            }
        };

        list.replaceAll(toUpper);

        if (!"A".equals(list.get(0))) {
            throw new RuntimeException("Element 0 not replaced correctly");
        }
        if (!"B".equals(list.get(1))) {
            throw new RuntimeException("Element 1 not replaced correctly");
        }
        if (!"C".equals(list.get(2))) {
            throw new RuntimeException("Element 2 not replaced correctly");
        }
    }
}
