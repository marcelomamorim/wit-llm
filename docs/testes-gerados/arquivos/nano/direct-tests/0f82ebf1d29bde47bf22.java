/* @test    @summary Test Vector.set method for valid and invalid indices    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testSetValidIndex();
        testSetInvalidIndex();
    }

    static void testSetValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        String oldValue = vector.set(1, "c");
        assert "b".equals(oldValue) : "Expected old value 'b', got " + oldValue;
        assert "c".equals(vector.get(1)) : "Expected new value 'c', got " + vector.get(1);
    }

    static void testSetInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.set(5, "d");
            assert false : "Expected ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
