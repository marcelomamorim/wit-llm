/* @test
   @summary Verifies that elementAt returns the correct element for valid indices and throws exception for invalid indices.
   @run main VectorWitupTest_Pa1793472a6fd
 */
import java.util.Vector;
public class VectorWitupTest_Pa1793472a6fd {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        
        // Test valid indices
        assert "a".equals(vector.elementAt(0)) : "Expected 'a' at index 0";
        assert "b".equals(vector.elementAt(1)) : "Expected 'b' at index 1";
        assert "c".equals(vector.elementAt(2)) : "Expected 'c' at index 2";
        
        // Test invalid index: negative
        try {
            vector.elementAt(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        
        // Test invalid index: equal to size
        try {
            vector.elementAt(3);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 3");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        
        // Test invalid index: greater than size
        try {
            vector.elementAt(10);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 10");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
