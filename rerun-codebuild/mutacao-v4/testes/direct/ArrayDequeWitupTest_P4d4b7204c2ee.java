/* @test
   @summary Verifies that addFirst method adds an element to the front of the ArrayDeque.
   @run main ArrayDequeWitupTest_P4d4b7204c2ee
 */
import java.util.ArrayDeque;
import java.util.Objects;

public class ArrayDequeWitupTest_P4d4b7204c2ee {
    public static void main(String[] args) {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addFirst("element");
        if (Objects.equals(deque.peekFirst(), "element")) {
            System.out.println("Test passed: addFirst adds element to front.");
        } else {
            throw new AssertionError("addFirst did not add element to front.");
        }
    }
}
