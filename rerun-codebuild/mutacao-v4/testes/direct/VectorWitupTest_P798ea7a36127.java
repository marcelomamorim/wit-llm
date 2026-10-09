/* @test
   @summary Tests removeElementAt method of Vector.
   @run main VectorWitupTest_P798ea7a36127
 */
import java.util.Vector;
public class VectorWitupTest_P798ea7a36127 {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        // Remove element at index 1 ("b")
        vector.removeElementAt(1);
        assert vector.size() == 2 : "Size should be 2 after removal";
        assert vector.get(0).equals("a") : "First element should be 'a'";
        assert vector.get(1).equals("c") : "Second element should be 'c'";
        // Remove element at index 0 ("a")
        vector.removeElementAt(0);
        assert vector.size() == 1 : "Size should be 1 after removal";
        assert vector.get(0).equals("c") : "Remaining element should be 'c'";
        // Remove last element (index 0)
        vector.removeElementAt(0);
        assert vector.isEmpty() : "Vector should be empty after removing all elements";
        // Test index out of bounds (negative)
        boolean exceptionThrown = false;
        try {
            vector.removeElementAt(-1);
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ArrayIndexOutOfBoundsException for negative index";
        // Test index out of bounds (greater than size)
        exceptionThrown = false;
        try {
            vector.removeElementAt(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ArrayIndexOutOfBoundsException for empty vector";
    }
}
