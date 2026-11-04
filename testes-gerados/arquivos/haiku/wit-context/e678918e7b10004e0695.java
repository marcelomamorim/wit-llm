/*
 * @test
 * @summary Witup-generated checks for Vector.setElementAt bounds behavior
 * @run main VectorWitupTest
 */
import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testEmptyVectorRejectsIndexZero();
        testCapacityDoesNotAllowSetBeyondSize();
        testIndexEqualToSizeRejected();
        testValidIndexReplacesElement();
        testNegativeIndexRejected();
    }

    private static void testEmptyVectorRejectsIndexZero() {
        Vector<String> v = new Vector<>();
        expectAioobe(v, "x", 0);
        if (v.size() != 0) {
            throw new RuntimeException("size changed after failed set");
        }
    }

    private static void testCapacityDoesNotAllowSetBeyondSize() {
        Vector<String> v = new Vector<>(10);
        expectAioobe(v, "x", 0);
        if (v.size() != 0) {
            throw new RuntimeException("size changed after failed set");
        }
    }

    private static void testIndexEqualToSizeRejected() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        expectAioobe(v, "x", 2);
        if (v.size() != 2 || !"a".equals(v.get(0)) || !"b".equals(v.get(1))) {
            throw new RuntimeException("contents changed after failed set");
        }
    }

    private static void testValidIndexReplacesElement() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.setElementAt("x", 1);
        if (v.size() != 2 || !"a".equals(v.get(0)) || !"x".equals(v.get(1))) {
            throw new RuntimeException("valid setElementAt failed");
        }
    }

    private static void testNegativeIndexRejected() {
        Vector<String> v = new Vector<>();
        v.add("a");
        expectAioobe(v, "x", -1);
        if (v.size() != 1 || !"a".equals(v.get(0))) {
            throw new RuntimeException("contents changed after failed set");
        }
    }

    private static void expectAioobe(Vector<String> v, String obj, int index) {
        try {
            v.setElementAt(obj, index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for index " + index);
    }
}
