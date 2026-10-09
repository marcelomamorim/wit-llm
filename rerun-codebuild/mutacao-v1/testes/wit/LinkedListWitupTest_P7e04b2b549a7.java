/* @test    @summary Test getFirst() method of LinkedList for null list case    @run main LinkedListWitupTest_P7e04b2b549a7 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_P7e04b2b549a7 {
    public static void main(String[] args) {
        testGetFirstEmptyList();
    }

    public static void testGetFirstEmptyList() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.getFirst();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // Test passes, exception was thrown as expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
