/*
 * @test
 * @summary Test java.util.Arrays.ArrayList.replaceAll(UnaryOperator<E>) for null operator and normal operation
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.UnaryOperator;
import java.util.Objects;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testReplaceAllNullOperator();
        testReplaceAllNormal();
    }

    static void testReplaceAllNullOperator() {
        // Create an Arrays.ArrayList with some elements
        Object[] elements = new Object[] {"a", "b", "c"};
        Arrays.ArrayList<Object> list = new Arrays.ArrayList<>(elements);

        try {
            list.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceAllNormal() {
        Object[] elements = new Object[] {"a", "b", "c"};
        Arrays.ArrayList<String> list = new Arrays.ArrayList<>(elements);

        // Replace each element with its uppercase version
        list.replaceAll(new UnaryOperator<String>() {
            public String apply(String s) {
                return s.toUpperCase();
            }
        });

        Object[] expected = new Object[] {"A", "B", "C"};
        Object[] actual = list.toArray();

        if (actual.length != expected.length) {
            throw new RuntimeException("Length mismatch after replaceAll");
        }

        for (int i = 0; i < actual.length; i++) {
            if (!Objects.equals(actual[i], expected[i])) {
                throw new RuntimeException("Element mismatch at index " + i + ": expected " + expected[i] + ", got " + actual[i]);
            }
        }
    }
}
