/* @test
   @summary Basic test for insertElementAt method, including boundary condition.
   @run main VectorWitupTest_Pf219e14daefc
 */
import java.util.Vector;
public class VectorWitupTest_Pf219e14daefc {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        // Valid insertion
        vector.insertElementAt("x", 1);
        if (!"x".equals(vector.get(1))) {
            throw new AssertionError("Failed to insert element at valid index");
        }
        // Boundary case: insert at index 0
        vector.insertElementAt("start", 0);
        if (!"start".equals(vector.get(0))) {
            throw new AssertionError("Failed to insert at start index");
        }
        // Boundary case: insert at last index
        int lastIndex = vector.size();
        vector.insertElementAt("end", lastIndex);
        if (!"end".equals(vector.get(lastIndex))) {
            throw new AssertionError("Failed to insert at last index");
        }
        // Exception case: insert at index greater than size
        try {
            vector.insertElementAt("fail", vector.size() + 1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        // Exception case: insert at negative index (not covered by WIT, but good to test)
        try {
            vector.insertElementAt("fail", -1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
