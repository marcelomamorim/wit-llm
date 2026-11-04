/*
 * @test
 * @summary Test Vector.add(int, E) method for correct insertion behavior.
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testAddAtBeginning();
        testAddAtEnd();
        testAddAtMiddle();
        testAddWithInvalidIndex();
    }

    static void testAddAtBeginning() {
        Vector<String> vector = new Vector<>();
        vector.add("second");
        vector.add("third");
        vector.add(0, "first");
        assert "first".equals(vector.get(0)) : "Failed to add at beginning";
        assert "second".equals(vector.get(1)) : "Incorrect element after addition at beginning";
        assert "third".equals(vector.get(2)) : "Incorrect element after addition at beginning";
    }

    static void testAddAtEnd() {
        Vector<String> vector = new Vector<>();
        vector.add("first");
        vector.add("second");
        vector.add(vector.size(), "third");
        assert "third".equals(vector.get(2)) : "Failed to add at end";
        assert vector.size() == 3 : "Vector size incorrect after adding at end";
    }

    static void testAddAtMiddle() {
        Vector<String> vector = new Vector<>();
        vector.add("first");
        vector.add("third");
        vector.add(1, "second");
        assert "second".equals(vector.get(1)) : "Failed to add at middle";
        assert "third".equals(vector.get(2)) : "Incorrect element after addition at middle";
    }

    static void testAddWithInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("only");
        boolean exceptionThrown = false;
        try {
            vector.add(5, "invalid");
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for invalid index";
    }
}
