/*
 * @test
 * @summary Test java.util.ArrayList.replaceAll(UnaryOperator) basic behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testReplaceAllWithIncrement();
        testReplaceAllWithNullOperator();
        testReplaceAllOnEmptyList();
    }

    private static void testReplaceAllWithIncrement() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        list.replaceAll(new UnaryOperator<Integer>() {
            public Integer apply(Integer i) {
                return i + 1;
            }
        });

        if (list.size() != 3) {
            throw new RuntimeException("Size changed after replaceAll");
        }
        if (!list.get(0).equals(2) || !list.get(1).equals(3) || !list.get(2).equals(4)) {
            throw new RuntimeException("replaceAll did not correctly replace elements");
        }
    }

    private static void testReplaceAllWithNullOperator() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        try {
            list.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException for null operator");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testReplaceAllOnEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        list.replaceAll(new UnaryOperator<String>() {
            public String apply(String s) {
                return "replaced";
            }
        });
        if (!list.isEmpty()) {
            throw new RuntimeException("Empty list should remain empty after replaceAll");
        }
    }
}
