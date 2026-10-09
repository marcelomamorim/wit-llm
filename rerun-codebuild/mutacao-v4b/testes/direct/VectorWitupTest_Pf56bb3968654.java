/* @test
   @summary Verifies the behavior of Vector.set method for valid and invalid indices.
   @run main VectorWitupTest_Pf56bb3968654
 */
import java.util.Vector;
public class VectorWitupTest_Pf56bb3968654 {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        
        // Test setting element at valid index
        String old = vector.set(1, "z");
        if (!"b".equals(old)) {
            throw new AssertionError("Expected old value 'b', but got: " + old);
        }
        if (!"z".equals(vector.get(1))) {
            throw new AssertionError("Element at index 1 should be 'z'");
        }
        
        // Test setting element at index 0
        old = vector.set(0, "x");
        if (!"a".equals(old)) {
            throw new AssertionError("Expected old value 'a', but got: " + old);
        }
        if (!"x".equals(vector.get(0))) {
            throw new AssertionError("Element at index 0 should be 'x'");
        }
        
        // Test setting element at last index
        int lastIndex = vector.size() - 1;
        old = vector.set(lastIndex, "y");
        if (!"c".equals(old)) {
            throw new AssertionError("Expected old value 'c', but got: " + old);
        }
        if (!"y".equals(vector.get(lastIndex))) {
            throw new AssertionError("Element at last index should be 'y'");
        }
        
        // Test setting element at index equal to size (should throw ArrayIndexOutOfBoundsException)
        try {
            vector.set(vector.size(), "outOfBounds");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index >= size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        
        // Test setting element at negative index (should throw ArrayIndexOutOfBoundsException)
        try {
            vector.set(-1, "negative");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
