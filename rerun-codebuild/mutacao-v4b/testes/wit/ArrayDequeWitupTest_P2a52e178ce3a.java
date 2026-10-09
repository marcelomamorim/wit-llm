/* @test
   @summary Tests addLast method of ArrayDeque for null input.
   @run main ArrayDequeWitupTest_P2a52e178ce3a
 */
import java.util.ArrayDeque;
public class ArrayDequeWitupTest_P2a52e178ce3a {
    public static void main(String[] args) {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.addLast(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }
}
