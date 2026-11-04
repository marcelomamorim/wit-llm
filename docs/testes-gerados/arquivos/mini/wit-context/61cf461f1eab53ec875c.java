/*
 * @test
 * @summary Test Vector.listIterator(int) for valid and invalid indices
 * @run main java.util.VectorWitupTest
 */

import java.util.ListIterator;
import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");

        // Test valid indices
        ListIterator<String> it0 = vector.listIterator(0);
        if (it0 == null) throw new AssertionError("listIterator(0) returned null");
        if (it0.nextIndex() != 0) throw new AssertionError("listIterator(0) nextIndex() != 0");

        ListIterator<String> it1 = vector.listIterator(1);
        if (it1 == null) throw new AssertionError("listIterator(1) returned null");
        if (it1.nextIndex() != 1) throw new AssertionError("listIterator(1) nextIndex() != 1");

        ListIterator<String> it3 = vector.listIterator(3);
        if (it3 == null) throw new AssertionError("listIterator(3) returned null");
        if (it3.nextIndex() != 3) throw new AssertionError("listIterator(3) nextIndex() != 3");

        // Test invalid indices: negative index
        try {
            vector.listIterator(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
        }

        // Test invalid indices: index greater than elementCount
        try {
            vector.listIterator(4);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 4");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
