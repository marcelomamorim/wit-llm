/* @test
   @summary Testa o método set de Vector para casos de índice inválido.
   @run main VectorWitupTest_Pf56bb3968654
 */
import java.util.Vector;
public class VectorWitupTest_Pf56bb3968654 {
    public static void main(String[] args) {
        testSetWithInvalidIndex();
    }
    private static void testSetWithInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.set(2, "c");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected exception
        }
        try {
            vector.set(-1, "d");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected exception
        }
    }
}
