/*
 * @test
 * @summary Test java.util.ArrayList.replaceAll(UnaryOperator) method
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testReplaceAllWithIncrement();
        testReplaceAllWithIdentity();
        testReplaceAllEmptyList();
        testReplaceAllWithNullOperator();
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
        if (list.size() != 3 || !list.get(0).equals(2) || !list.get(1).equals(3) || !list.get(2).equals(4)) {
            throw new RuntimeException("testReplaceAllWithIncrement failed: " + list);
        }
    }

    private static void testReplaceAllWithIdentity() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.replaceAll(new UnaryOperator<String>() {
            public String apply(String s) {
                return s;
            }
        });
        if (list.size() != 2 || !"a".equals(list.get(0)) || !"b".equals(list.get(1))) {
            throw new RuntimeException("testReplaceAllWithIdentity failed: " + list);
        }
    }

    private static void testReplaceAllEmptyList() {
        ArrayList<Object> list = new ArrayList<>();
        list.replaceAll(new UnaryOperator<Object>() {
            public Object apply(Object o) {
                return new Object();
            }
        });
        if (!list.isEmpty()) {
            throw new RuntimeException("testReplaceAllEmptyList failed: list should remain empty");
        }
    }

    private static void testReplaceAllWithNullOperator() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        try {
            list.replaceAll(null);
            throw new RuntimeException("testReplaceAllWithNullOperator failed: expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
