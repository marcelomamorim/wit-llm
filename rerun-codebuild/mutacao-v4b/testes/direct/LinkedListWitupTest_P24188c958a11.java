/* @test
   @summary Verifies that removeLast() throws NoSuchElementException when list is empty.
   @run main LinkedListWitupTest_P24188c958a11
 */
import java.util.*;
public class LinkedListWitupTest_P24188c958a11 {
    public static void main(String[] args) {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.removeLast();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // expected exception
        }
    }
}
