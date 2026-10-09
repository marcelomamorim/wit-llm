/*
 * @test
 * @summary Test removeElementAt method of Vector class
 * @run main VectorWitupTest_P5505a5d1ddab
 */

import java.util.Vector;

public class VectorWitupTest_P5505a5d1ddab {

    public static void main(String[] args) {
        testRemoveElementAt();
        testRemoveElementAtNegativeIndex();
        testRemoveElementAtIndexEqualSize();
    }

    static void testRemoveElementAt() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        int initialSize = vector.size();
        vector.removeElementAt(1); // remove "b"
        assert vector.size() == initialSize - 1 : "Size should decrease by 1";
        assert !vector.contains("b") : "Vector should not contain 'b'";
        assert vector.get(0).equals("a") : "First element should be 'a'";
        assert vector.get(1).equals("c") : "Second element should be 'c'";
    }

    static void testRemoveElementAtNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        try {
            vector.removeElementAt(-1);
            assert false : "Expected ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRemoveElementAtIndexEqualSize() {
        Vector<String> vector = new Vector<>();
        vector.add("m");
        vector.add("n");
        int size = vector.size();
        try {
            vector.removeElementAt(size); // index equal to size, should throw
            assert false : "Expected ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
