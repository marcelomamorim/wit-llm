/* @test    @summary Test removeElementAt method for Vector class    @run main VectorWitupTest_P07cac8861581 */

import java.util.Vector;

public class VectorWitupTest_P07cac8861581 {

    public static void main(String[] args) {
        testRemoveElementAtValidIndex();
        testRemoveElementAtNegativeIndex();
        testRemoveElementAtIndexEqualToSize();
        testRemoveElementAtIndexGreaterThanSize();
    }

    private static void testRemoveElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        vector.removeElementAt(1);
        assert vector.size() == 2 : "Size should be 2 after removal";
        assert vector.get(0).equals("a") : "First element should be 'a'";
        assert vector.get(1).equals("c") : "Second element should be 'c'";
    }

    private static void testRemoveElementAtNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        boolean exceptionThrown = false;
        try {
            vector.removeElementAt(-1);
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ArrayIndexOutOfBoundsException for negative index";
    }

    private static void testRemoveElementAtIndexEqualToSize() {
        Vector<String> vector = new Vector<>();
        vector.add("y");
        vector.add("z");
        boolean exceptionThrown = false;
        try {
            vector.removeElementAt(vector.size());
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ArrayIndexOutOfBoundsException when index equals size";
    }

    private static void testRemoveElementAtIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("m");
        boolean exceptionThrown = false;
        try {
            vector.removeElementAt(10);
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ArrayIndexOutOfBoundsException for index greater than size";
    }
}
