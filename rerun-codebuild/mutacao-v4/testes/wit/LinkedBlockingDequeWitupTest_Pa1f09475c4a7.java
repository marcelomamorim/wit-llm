/* @test
   @summary Testa o método offerFirst de LinkedBlockingDeque para comportamentos observáveis.
   @run main LinkedBlockingDequeWitupTest_Pa1f09475c4a7
 */
import java.util.concurrent.LinkedBlockingDeque;
public class LinkedBlockingDequeWitupTest_Pa1f09475c4a7 {
    public static void main(String[] args) {
        testOfferFirstNull();
        testOfferFirstNormal();
    }
    private static void testOfferFirstNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.offerFirst(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when offerFirst(null)");
        }
    }
    private static void testOfferFirstNormal() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerFirst("element");
        if (!result) {
            throw new AssertionError("offerFirst should return true for normal element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after offerFirst");
        }
        String head = deque.peekFirst();
        if (!"element".equals(head)) {
            throw new AssertionError("Head element mismatch: expected 'element', got " + head);
        }
    }
}
