/* @test    @summary Test removeElementAt for Vector, covering valid and boundary cases    @run main VectorWitupTest_P5505a5d1ddab */

import java.util.Vector;

public class VectorWitupTest_P5505a5d1ddab {
    public static void main(String[] args) {
        testRemoveElementAtValidIndex();
        testRemoveElementAtNegativeIndex();
        testRemoveElementAtIndexEqualToSize();
        testRemoveElementAtIndexGreaterThanSize();
        System.out.println("All tests passed.");
    }

    static void testRemoveElementAtValidIndex() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        v.add(3);
        v.removeElementAt(1); // remove element at index 1
        assert v.size() == 2 : "Size should be 2 after removal";
        assert v.get(0) == 1 : "First element should be 1";
        assert v.get(1) == 3 : "Second element should be 3";
    }

    static void testRemoveElementAtNegativeIndex() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        try {
            v.removeElementAt(-1);
            assert false : "Expected ArrayIndexOutOfBoundsException for negative index";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRemoveElementAtIndexEqualToSize() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        try {
            v.removeElementAt(v.size()); // index == size
            assert false : "Expected ArrayIndexOutOfBoundsException for index equal to size";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRemoveElementAtIndexGreaterThanSize() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        try {
            v.removeElementAt(10); // index > size
            assert false : "Expected ArrayIndexOutOfBoundsException for index > size";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
