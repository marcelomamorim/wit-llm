/* @test    @summary Test addFirst method of ArrayDeque for null input causing NullPointerException    @run main ArrayDequeWitupTest_P2f42a36fe1ee */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest_P2f42a36fe1ee {

    public static void main(String[] args) {
        testAddFirstNullThrows();
    }

    public static void testAddFirstNullThrows() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        boolean threw = false;
        try {
            deque.addFirst(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when addFirst(null)");
        }
    }
}
