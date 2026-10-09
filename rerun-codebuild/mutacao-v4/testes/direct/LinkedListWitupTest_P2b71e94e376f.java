/* @test
   @summary Tests getFirst() method of LinkedList for normal and exceptional behavior.
   @run main LinkedListWitupTest_P2b71e94e376f
 */
import java.util.*;
public class LinkedListWitupTest_P2b71e94e376f {
    public static void main(String[] args) {
        testGetFirstWithElements();
        testGetFirstEmptyList();
    }
    private static void testGetFirstWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        String first = list.getFirst();
        if (!"a".equals(first)) {
            throw new AssertionError("Expected 'a', but got: " + first);
        }
    }
    private static void testGetFirstEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.getFirst();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
