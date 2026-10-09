/* @test
   @summary Tests the remove(int index) method of java.util.Vector.
   @run main VectorWitupTest_Pc0db75744c4e
 */
import java.util.Vector;
public class VectorWitupTest_Pc0db75744c4e {
    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveIndexOutOfBounds();
    }
    private static void testRemoveValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        String removed = v.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected removed element to be 'b', but was: " + removed);
        }
        if (v.size() != 2) {
            throw new AssertionError("Expected size 2 after removal, but was: " + v.size());
        }
        if (!v.get(0).equals("a") || !v.get(1).equals("c")) {
            throw new AssertionError("Remaining elements do not match expected values");
        }
    }
    private static void testRemoveIndexOutOfBounds() {
        Vector<String> v = new Vector<>();
        v.add("x");
        try {
            v.remove(1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 1 in vector of size 1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        try {
            v.remove(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        try {
            v.remove(100);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for large index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
