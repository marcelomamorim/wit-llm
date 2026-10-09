/* @test
   @summary Tests the setElementAt method of java.util.Vector.
   @run main VectorWitupTest_P3c9de0b7cacf
 */
import java.util.Vector;
public class VectorWitupTest_P3c9de0b7cacf {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        // Test setting element at valid index
        vector.setElementAt("x", 1);
        assert "x".equals(vector.get(1)) : "Element at index 1 should be 'x'";
        // Test setting element at index 0
        vector.setElementAt("y", 0);
        assert "y".equals(vector.get(0)) : "Element at index 0 should be 'y'";
        // Test setting element at last valid index
        vector.setElementAt("z", 2);
        assert "z".equals(vector.get(2)) : "Element at index 2 should be 'z'";
        // Test setting element at index equal to size (should throw ArrayIndexOutOfBoundsException)
        try {
            vector.setElementAt("out", 3);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 3");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        // Test setting element at negative index (should throw ArrayIndexOutOfBoundsException)
        try {
            vector.setElementAt("neg", -1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
