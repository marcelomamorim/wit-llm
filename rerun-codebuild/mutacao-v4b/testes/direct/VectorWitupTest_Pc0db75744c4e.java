/* @test
   @summary Tests the remove(int index) method of java.util.Vector.
   @run main VectorWitupTest_Pc0db75744c4e
 */
import java.util.Vector;
public class VectorWitupTest_Pc0db75744c4e {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        String removed = vector.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected 'b', got: " + removed);
        }
        if (vector.size() != 2) {
            throw new AssertionError("Expected size 2, got: " + vector.size());
        }
        if (!"a".equals(vector.get(0))) {
            throw new AssertionError("First element should be 'a'");
        }
        if (!"c".equals(vector.get(1))) {
            throw new AssertionError("Second element should be 'c'");
        }
        // Test removing at invalid index
        boolean exceptionThrown = false;
        try {
            vector.remove(5);
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for invalid index");
        }
    }
}
