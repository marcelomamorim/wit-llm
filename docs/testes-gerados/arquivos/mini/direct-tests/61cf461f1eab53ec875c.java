/*
 * @test
 * @summary Test java.util.Vector.listIterator(int) method for valid and invalid indices
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.ListIterator;
import java.lang.IndexOutOfBoundsException;

public class VectorWitupTest {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("zero");
        vector.add("one");
        vector.add("two");

        // Test valid indices
        ListIterator<String> it0 = vector.listIterator(0);
        if (it0.nextIndex() != 0) throw new RuntimeException("Expected nextIndex 0 at index 0");

        ListIterator<String> it1 = vector.listIterator(1);
        if (it1.nextIndex() != 1) throw new RuntimeException("Expected nextIndex 1 at index 1");

        ListIterator<String> it3 = vector.listIterator(3);
        if (it3.nextIndex() != 3) throw new RuntimeException("Expected nextIndex 3 at index 3");

        // Test invalid negative index
        try {
            vector.listIterator(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
        }

        // Test invalid index greater than size
        try {
            vector.listIterator(4);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index 4");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
