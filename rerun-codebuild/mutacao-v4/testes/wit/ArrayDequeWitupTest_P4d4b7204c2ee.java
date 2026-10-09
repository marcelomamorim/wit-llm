/* @test
   @summary Tests addFirst method of ArrayDeque for null input.
   @run main ArrayDequeWitupTest_P4d4b7204c2ee
 */
import java.util.ArrayDeque;
public class ArrayDequeWitupTest_P4d4b7204c2ee {
    public static void main(String[] args) {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        try {
            deque.addFirst(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes if exception is thrown
        }
    }
}
