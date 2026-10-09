/* @test
   @summary Tests insertElementAt method of Vector for valid and invalid indices.
   @run main VectorWitupTest_Pf219e14daefc
 */
import java.util.Vector;
public class VectorWitupTest_Pf219e14daefc {
    public static void main(String[] args) {
        testInsertElementAtValidIndex();
        testInsertElementAtInvalidIndex();
    }
    private static void testInsertElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.insertElementAt("x", 1);
        assert "a".equals(vector.get(0)) : "First element should be 'a'";
        assert "x".equals(vector.get(1)) : "Second element should be 'x'";
        assert "b".equals(vector.get(2)) : "Third element should be 'b'";
        assert vector.size() == 3 : "Size should be 3";
    }
    private static void testInsertElementAtInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        boolean exceptionThrown = false;
        try {
            vector.insertElementAt("x", 5); // invalid index, greater than size
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ArrayIndexOutOfBoundsException";
    }
}
