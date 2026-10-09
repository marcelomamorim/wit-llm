/* @test
   @summary Basic tests for LinkedBlockingDeque.offerLast method.
   @run main LinkedBlockingDequeWitupTest_Pd67bc7641158
 */
import java.util.concurrent.LinkedBlockingDeque;
public class LinkedBlockingDequeWitupTest_Pd67bc7641158 {
    public static void main(String[] args) {
        testOfferLastWithNull();
        testOfferLastWithValidElement();
    }
    private static void testOfferLastWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.offerLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when offering null");
        }
    }
    private static void testOfferLastWithValidElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerLast("element");
        if (!result) {
            throw new AssertionError("offerLast should return true for valid element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after offerLast");
        }
        String head = deque.peekLast();
        if (!"element".equals(head)) {
            throw new AssertionError("Last element should be 'element', but was: " + head);
        }
    }
}
