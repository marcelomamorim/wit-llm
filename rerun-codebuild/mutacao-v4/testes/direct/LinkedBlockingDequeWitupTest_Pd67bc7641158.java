/* @test
   @summary Verifies offerLast method behavior of LinkedBlockingDeque.
   @run main LinkedBlockingDequeWitupTest_Pd67bc7641158
 */
import java.util.concurrent.LinkedBlockingDeque;
import java.util.Objects;
public class LinkedBlockingDequeWitupTest_Pd67bc7641158 {
    public static void main(String[] args) {
        testOfferLastWithValidElement();
        testOfferLastWithNullElement();
    }

    private static void testOfferLastWithValidElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerLast("element");
        if (!result) {
            throw new AssertionError("offerLast should return true for non-null element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should contain the element after offerLast");
        }
        String head = deque.peekFirst();
        if (!"element".equals(head)) {
            throw new AssertionError("First element should be 'element', but was: " + head);
        }
    }

    private static void testOfferLastWithNullElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.offerLast(null);
            throw new AssertionError("offerLast should throw NullPointerException for null element");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
