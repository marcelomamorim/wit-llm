/* @test
   @summary Tests removeFirst() method of LinkedList.
   @run main LinkedListWitupTest_P50048851c372
 */
import java.util.LinkedList;
public class LinkedListWitupTest_P50048851c372 {
    public static void main(String[] args) {
        testRemoveFirstEmpty();
        testRemoveFirstNonEmpty();
    }
    private static void testRemoveFirstEmpty() {
        LinkedList<Object> list = new LinkedList<>();
        boolean exceptionThrown = false;
        try {
            list.removeFirst();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException when removing first from empty list");
        }
    }
    private static void testRemoveFirstNonEmpty() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element");
        String removed = list.removeFirst();
        if (!"element".equals(removed)) {
            throw new AssertionError("removeFirst() did not return the expected element");
        }
        if (!list.isEmpty()) {
            throw new AssertionError("List should be empty after removeFirst()");
        }
    }
}
