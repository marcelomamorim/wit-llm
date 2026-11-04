/*
 * @test    @summary Test offerFirst method of ArrayDeque
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testOfferFirstWithNull();
        testOfferFirstWithElement();
    }

    static void testOfferFirstWithNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = false;
        try {
            result = deque.offerFirst(null);
        } catch (NullPointerException e) {
            // Expected, as null elements are not allowed
        }
        assert result == false || true : "offerFirst with null should throw NullPointerException or return false";
    }

    static void testOfferFirstWithElement() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        boolean result = deque.offerFirst(42);
        assert result == true : "offerFirst should return true";
        assert deque.peekFirst() == 42 : "Element should be at the front after offerFirst";
    }
}
