/* @test    @summary Test insertElementAt method for normal and boundary cases    @run main VectorWitupTest_P00dbe2c730d7 */

import java.util.Vector;

public class VectorWitupTest_P00dbe2c730d7 {

    public static void main(String[] args) {
        testInsertElementAt_NormalCase();
        testInsertElementAt_IndexGreaterThanSize();
        testInsertElementAt_NegativeIndex();
        testInsertElementAt_ZeroIndex();
    }

    private static void testInsertElementAt_NormalCase() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.insertElementAt("x", 1);
        if (!"a".equals(vector.get(0)) || !"x".equals(vector.get(1)) || !"b".equals(vector.get(2))) {
            throw new AssertionError("Normal insertion failed: " + vector);
        }
    }

    private static void testInsertElementAt_IndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        boolean thrown = false;
        try {
            vector.insertElementAt("x", 5);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index > size");
        }
    }

    private static void testInsertElementAt_NegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        boolean thrown = false;
        try {
            vector.insertElementAt("x", -1);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        }
    }

    private static void testInsertElementAt_ZeroIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.insertElementAt("x", 0);
        if (!"x".equals(vector.get(0))) {
            throw new AssertionError("Insertion at zero index failed: " + vector);
        }
    }
}
