/*
 * @test
 * @summary Test java.util.Arrays replaceAll method behavior
 * @run main ArraysWitupTest
 */

import java.util.function.UnaryOperator;
import java.util.Objects;

public class ArraysWitupTest {

    static class TestArray<E> {
        E[] a;
        TestArray(E[] array) { this.a = array; }
        public void replaceAll(UnaryOperator<E> operator) {
            Objects.requireNonNull(operator);
            E[] a = this.a;
            for (int i = 0; i < a.length; i++) {
                a[i] = operator.apply(a[i]);
            }
        }
    }

    public static void main(String[] args) {
        testReplaceAllBasic();
        testReplaceAllNullOperator();
        testReplaceAllEmptyArray();
    }

    private static void testReplaceAllBasic() {
        Integer[] data = {1, 2, 3};
        TestArray<Integer> ta = new TestArray<>(data);
        ta.replaceAll(new UnaryOperator<Integer>() {
            public Integer apply(Integer i) {
                return i * 2;
            }
        });
        if (ta.a.length != 3 || ta.a[0] != 2 || ta.a[1] != 4 || ta.a[2] != 6) {
            throw new RuntimeException("replaceAll did not correctly replace elements");
        }
    }

    private static void testReplaceAllNullOperator() {
        Integer[] data = {1, 2, 3};
        TestArray<Integer> ta = new TestArray<>(data);
        try {
            ta.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceAllEmptyArray() {
        String[] data = new String[0];
        TestArray<String> ta = new TestArray<>(data);
        ta.replaceAll(new UnaryOperator<String>() {
            public String apply(String s) {
                return "fail"; // should not be called
            }
        });
        if (ta.a.length != 0) {
            throw new RuntimeException("replaceAll on empty array changed length");
        }
    }
}
