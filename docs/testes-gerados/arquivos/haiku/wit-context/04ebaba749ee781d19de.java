/*
 * @test
 * @summary Tests Vector.replaceAll: NullPointerException for null operator, element replacement, and ConcurrentModificationException on structural modification by the operator
 * @run main VectorWitupTest
 */
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Vector;
import java.util.function.UnaryOperator;

public class VectorWitupTest {

    public static void main(String[] args) {
        testNullOperatorThrowsNpe();
        testReplaceAllAppliesOperator();
        testStructuralModificationThrowsCme();
    }

    static void testNullOperatorThrowsNpe() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b"));
        boolean thrown = false;
        try {
            v.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null operator");
        }
        if (!v.equals(Arrays.asList("a", "b"))) {
            throw new RuntimeException("Vector modified after NullPointerException: " + v);
        }
    }

    static void testReplaceAllAppliesOperator() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c"));
        UnaryOperator<String> op = s -> s.toUpperCase();
        v.replaceAll(op);
        if (!v.equals(Arrays.asList("A", "B", "C"))) {
            throw new RuntimeException("Unexpected result: " + v);
        }
        if (v.size() != 3) {
            throw new RuntimeException("Unexpected size: " + v.size());
        }
    }

    static void testStructuralModificationThrowsCme() {
        final Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c"));
        UnaryOperator<String> op = s -> {
            if (s.equals("a")) {
                v.add("x");
            }
            return s.toUpperCase();
        };
        boolean thrown = false;
        try {
            v.replaceAll(op);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ConcurrentModificationException");
        }
    }
}
