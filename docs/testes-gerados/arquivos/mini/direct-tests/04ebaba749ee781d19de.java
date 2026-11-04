/*
 * @test
 * @summary Test Vector.replaceAll(UnaryOperator) for correct replacement and fail-fast behavior
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.function.UnaryOperator;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class VectorWitupTest {
    public static void main(String[] args) {
        testReplaceAllBasic();
        testReplaceAllNullOperator();
        testReplaceAllConcurrentModification();
    }

    private static void testReplaceAllBasic() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");

        UnaryOperator<String> op = new UnaryOperator<String>() {
            public String apply(String s) {
                return s.toUpperCase();
            }
        };

        v.replaceAll(op);

        if (!"A".equals(v.get(0)))
            throw new AssertionError("Expected A at index 0 but found " + v.get(0));
        if (!"B".equals(v.get(1)))
            throw new AssertionError("Expected B at index 1 but found " + v.get(1));
        if (!"C".equals(v.get(2)))
            throw new AssertionError("Expected C at index 2 but found " + v.get(2));
    }

    private static void testReplaceAllNullOperator() {
        Vector<String> v = new Vector<>();
        v.add("x");
        try {
            v.replaceAll(null);
            throw new AssertionError("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceAllConcurrentModification() {
        final Vector<String> v = new Vector<>();
        v.add("one");
        v.add("two");

        UnaryOperator<String> op = new UnaryOperator<String>() {
            public String apply(String s) {
                if ("one".equals(s)) {
                    v.add("three"); // structural modification during replaceAll
                }
                return s;
            }
        };

        try {
            v.replaceAll(op);
            throw new AssertionError("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
