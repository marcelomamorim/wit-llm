/* @test
   @summary Test offerFirst method of LinkedBlockingDeque.
   @run main LinkedBlockingDequeWitupTest_Pa1f09475c4a7
 */
import java.util.concurrent.LinkedBlockingDeque;
public class LinkedBlockingDequeWitupTest_Pa1f09475c4a7 {
    public static void main(String[] args) {
        testOfferFirstWithNull();
        testOfferFirstWithValidElement();
    }
    private static void testOfferFirstWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.offerFirst(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when offering null");
        }
    }
    private static void testOfferFirstWithValidElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerFirst("element");
        if (!result) {
            throw new AssertionError("offerFirst should return true for non-null element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after offerFirst");
        }
        String head = deque.peekFirst();
        if (!"element".equals(head)) {
            throw new AssertionError("Head element should be 'element', but was: " + head);
        }
    }
}
