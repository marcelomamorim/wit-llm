/* @test    @summary Test setElementAt method of Vector class    @run main VectorWitupTest_P21039156dcc6 */

import java.util.Vector;

public class VectorWitupTest_P21039156dcc6 {
    public static void main(String[] args) {
        testSetElementAtValidIndex();
        testSetElementAtNegativeIndex();
        testSetElementAtIndexEqualToSize();
    }

    private static void testSetElementAtValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.setElementAt("x", 1);
        assert v.get(1).equals("x") : "Expected element at index 1 to be 'x'";
        assert v.size() == 2 : "Size should remain 2";
    }

    private static void testSetElementAtNegativeIndex() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.setElementAt("x", -1);
            assert false : "Expected ArrayIndexOutOfBoundsException for negative index";
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testSetElementAtIndexEqualToSize() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.setElementAt("x", v.size());
            assert false : "Expected ArrayIndexOutOfBoundsException for index equal to size";
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
