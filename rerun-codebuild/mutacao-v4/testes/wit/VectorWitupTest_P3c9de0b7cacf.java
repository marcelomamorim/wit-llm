/* @test
   @summary Testa o método setElementAt de Vector para comportamentos observáveis.
   @run main VectorWitupTest_P3c9de0b7cacf
 */
import java.util.Vector;
public class VectorWitupTest_P3c9de0b7cacf {
    public static void main(String[] args) {
        testSetElementAtWithValidIndex();
        testSetElementAtWithNegativeIndex();
        testSetElementAtWithIndexEqualToSize();
    }

    private static void testSetElementAtWithValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.setElementAt("c", 1);
        if (!"c".equals(vector.get(1))) {
            throw new AssertionError("Expected 'c' at index 1");
        }
    }

    private static void testSetElementAtWithNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.setElementAt("b", -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testSetElementAtWithIndexEqualToSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.setElementAt("b", vector.size());
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
