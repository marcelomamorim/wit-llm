/* @test
   @summary Tests the get method of Vector for valid and invalid indices.
   @run main VectorWitupTest_Paff5f93a473b
 */
import java.util.Vector;
public class VectorWitupTest_Paff5f93a473b {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        
        // Test valid index
        String element = vector.get(1);
        if (!"b".equals(element)) {
            throw new AssertionError("Expected 'b' at index 1, but got: " + element);
        }
        
        // Test index 0
        element = vector.get(0);
        if (!"a".equals(element)) {
            throw new AssertionError("Expected 'a' at index 0, but got: " + element);
        }
        
        // Test last valid index
        element = vector.get(2);
        if (!"c".equals(element)) {
            throw new AssertionError("Expected 'c' at index 2, but got: " + element);
        }
        
        // Test out-of-bounds index (negative)
        try {
            vector.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        
        // Test out-of-bounds index (exceeds size)
        try {
            vector.get(3);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 3");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
