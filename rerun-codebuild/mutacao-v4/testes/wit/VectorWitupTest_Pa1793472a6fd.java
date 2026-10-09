/* @test
   @summary Testa o método elementAt de java.util.Vector para verificar comportamento normal e exceções.
   @run main VectorWitupTest_Pa1793472a6fd
 */
import java.util.Vector;
public class VectorWitupTest_Pa1793472a6fd {
    public static void main(String[] args) {
        testElementAtValidIndex();
        testElementAtIndexEqualToSize();
        testElementAtIndexGreaterThanSize();
    }

    private static void testElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        String result = vector.elementAt(1);
        if (!"b".equals(result)) {
            throw new AssertionError("Expected 'b', got: " + result);
        }
    }

    private static void testElementAtIndexEqualToSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.elementAt(2); // size é 2, index válido até 1
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // esperado
        }
    }

    private static void testElementAtIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.elementAt(3); // maior que size
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index greater than size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // esperado
        }
    }
}
