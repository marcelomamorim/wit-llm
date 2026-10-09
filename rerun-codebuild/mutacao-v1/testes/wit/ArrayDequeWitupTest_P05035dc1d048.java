/* @test    @summary Test addLast method of ArrayDeque for null input to verify NullPointerException is thrown.    @run main ArrayDequeWitupTest_P05035dc1d048 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest_P05035dc1d048 {

    public static void main(String[] args) {
        testAddLastNullInput();
    }

    public static void testAddLastNullInput() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.addLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when adding null to ArrayDeque.addLast()");
        }
    }
}
