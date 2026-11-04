/*
 * @test
 * @summary Witup-generated tests for Vector.forEach null handling, ordering and concurrent modification
 * @run main VectorWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyVectorDoesNotInvokeAction();
        testVisitsElementsInOrder();
        testStructuralModificationThrowsCME();
    }

    static void testNullActionThrowsNPE() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.forEach(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyVectorDoesNotInvokeAction() {
        Vector<String> v = new Vector<>();
        final int[] calls = {0};
        v.forEach(e -> calls[0]++);
        if (calls[0] != 0) {
            throw new RuntimeException("action invoked on empty vector: " + calls[0]);
        }
    }

    static void testVisitsElementsInOrder() {
        Vector<String> v = new Vector<>();
        v.add("x");
        v.add("y");
        v.add("z");
        final StringBuilder sb = new StringBuilder();
        v.forEach(e -> sb.append(e));
        if (!sb.toString().equals("xyz")) {
            throw new RuntimeException("unexpected order: " + sb);
        }
    }

    static void testStructuralModificationThrowsCME() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        try {
            v.forEach(e -> v.add("c"));
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
        if (v.size() != 3) {
            throw new RuntimeException("unexpected size: " + v.size());
        }
    }
}
