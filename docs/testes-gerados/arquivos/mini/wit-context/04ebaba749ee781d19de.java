/*
 * @test
 * @summary Test Vector.replaceAll(UnaryOperator) for null operator and concurrent modification
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.function.UnaryOperator;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class VectorWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullOperator();
        testReplaceAllConcurrentModification();
    }

    private static void testReplaceAllNullOperator() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.replaceAll(null);
            throw new Error("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceAllConcurrentModification() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");

        // Create a UnaryOperator that modifies the vector during replaceAll
        UnaryOperator<String> op = new UnaryOperator<String>() {
            private int count = 0;
            public String apply(String s) {
                count++;
                if (count == 1) {
                    // Structural modification during iteration to trigger ConcurrentModificationException
                    v.add("c");
                }
                return s.toUpperCase();
            }
        };

        try {
            v.replaceAll(op);
            throw new Error("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
