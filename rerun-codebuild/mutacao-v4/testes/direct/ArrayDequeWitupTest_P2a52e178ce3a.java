/* @test
   @summary Verifies that addLast correctly adds elements to the ArrayDeque.
   @run main ArrayDequeWitupTest_P2a52e178ce3a
 */
import java.util.ArrayDeque;
public class ArrayDequeWitupTest_P2a52e178ce3a {
    public static void main(String[] args) {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("element1");
        deque.addLast("element2");
        if (deque.size() != 2) {
            throw new AssertionError("Expected size 2, but got " + deque.size());
        }
        String first = deque.peekFirst();
        String last = deque.peekLast();
        if (!"element1".equals(first)) {
            throw new AssertionError("Expected first element to be 'element1', but was '" + first + "'");
        }
        if (!"element2".equals(last)) {
            throw new AssertionError("Expected last element to be 'element2', but was '" + last + "'");
        }
        deque.addLast(null);
        // Expect NullPointerException, as addLast throws NPE on null
        try {
            deque.addLast(null);
            throw new AssertionError("Expected NullPointerException when adding null");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
