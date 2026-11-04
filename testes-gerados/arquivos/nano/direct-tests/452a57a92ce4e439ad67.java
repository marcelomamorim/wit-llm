/* @test    @summary Test removeElementAt method of Vector for valid and invalid indices    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRemoveElementAtValidIndex();
        testRemoveElementAtNegativeIndex();
        testRemoveElementAtIndexEqualToSize();
    }

    static void testRemoveElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        vector.removeElementAt(1);
        assert vector.size() == 2 : "Size should be 2 after removal";
        assert vector.get(0).equals("a") : "First element should be 'a'";
        assert vector.get(1).equals("c") : "Second element should be 'c'";
    }

    static void testRemoveElementAtNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        try {
            vector.removeElementAt(-1);
            assert false : "Expected ArrayIndexOutOfBoundsException for negative index";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRemoveElementAtIndexEqualToSize() {
        Vector<String> vector = new Vector<>();
        vector.add("y");
        vector.add("z");
        try {
            vector.removeElementAt(2); // size is 2, valid indices are 0 and 1
            assert false : "Expected ArrayIndexOutOfBoundsException for index equal to size";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
