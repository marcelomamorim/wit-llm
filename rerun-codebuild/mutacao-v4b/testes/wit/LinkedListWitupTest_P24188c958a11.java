/* @test
   @summary Testa o método removeLast() de LinkedList para comportamento normal e exceções.
   @run main LinkedListWitupTest_P24188c958a11
 */
import java.util.LinkedList;
public class LinkedListWitupTest_P24188c958a11 {
    public static void main(String[] args) {
        testRemoveLastNormal();
        testRemoveLastEmpty();
    }
    private static void testRemoveLastNormal() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        String removed = list.removeLast();
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected 'b', got: " + removed);
        }
        if (list.size() != 1 || !"a".equals(list.get(0))) {
            throw new AssertionError("List state incorrect after removeLast");
        }
    }
    private static void testRemoveLastEmpty() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.removeLast();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (java.util.NoSuchElementException e) {
            // expected
        }
    }
}
