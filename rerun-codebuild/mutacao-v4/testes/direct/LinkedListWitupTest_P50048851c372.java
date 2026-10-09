/* @test
   @summary Verifies that removeFirst() throws NoSuchElementException when list is empty.
   @run main LinkedListWitupTest_P50048851c372
 */
import java.util.*;
public class LinkedListWitupTest_P50048851c372 {
    public static void main(String[] args) {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.removeFirst();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // expected exception
        }
    }
}
