/* @test    @summary Test for ArrayDeque.offerFirst with null input to verify NullPointerException is thrown.    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testOfferFirstNull();
    }

    public static void testOfferFirstNull() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        try {
            deque.offerFirst(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException expected) {
            // Test passes as exception is expected
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
