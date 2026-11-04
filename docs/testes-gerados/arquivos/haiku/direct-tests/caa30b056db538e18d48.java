/*
 * @test
 * @summary Verifies that the list returned by Collections.nCopies rejects out-of-range indexes in get(int) and returns the repeated element otherwise
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        List<String> list = Collections.nCopies(3, "x");
        if (!"x".equals(list.get(0)) || !"x".equals(list.get(1)) || !"x".equals(list.get(2))) {
            throw new AssertionError("get(int) returned an unexpected element");
        }
        checkOutOfBounds(list, -1);
        checkOutOfBounds(list, 3);
        checkOutOfBounds(Collections.nCopies(0, "y"), 0);
    }

    static void checkOutOfBounds(List<String> list, int index) {
        try {
            list.get(index);
            throw new AssertionError("No exception for index " + index);
        } catch (IndexOutOfBoundsException e) {
            String expected = "Index: " + index + ", Size: " + list.size();
            if (!expected.equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }
}
