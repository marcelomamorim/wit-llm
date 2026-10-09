/* @test
   @summary Testa o método getFirst() de LinkedList para comportamento normal e exceções.
   @run main LinkedListWitupTest_P2b71e94e376f
 */
import java.util.LinkedList;
public class LinkedListWitupTest_P2b71e94e376f {
    public static void main(String[] args) {
        testGetFirstNormal();
        testGetFirstEmpty();
    }
    private static void testGetFirstNormal() {
        LinkedList<String> list = new LinkedList<>();
        list.add("elemento");
        String result = list.getFirst();
        if (!"elemento".equals(result)) {
            throw new AssertionError("Expected 'elemento', got: " + result);
        }
    }
    private static void testGetFirstEmpty() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.getFirst();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (java.util.NoSuchElementException e) {
            // expected
        }
    }
}
