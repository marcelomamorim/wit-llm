/*
 * @test
 * @summary Verify that the get method of the singleton list returned by Collections.singletonList returns the element at index 0 and throws IndexOutOfBoundsException for any other index
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        List<String> list = Collections.singletonList("a");
        if (!"a".equals(list.get(0))) {
            throw new AssertionError("get(0) should return the single element");
        }

        List<String> nullList = Collections.singletonList(null);
        if (nullList.get(0) != null) {
            throw new AssertionError("get(0) should return null for a null element");
        }

        checkOutOfBounds(list, 1);
        checkOutOfBounds(list, -1);
        checkOutOfBounds(list, Integer.MAX_VALUE);
    }

    private static void checkOutOfBounds(List<String> list, int index) {
        try {
            list.get(index);
            throw new AssertionError("expected IndexOutOfBoundsException for index " + index);
        } catch (IndexOutOfBoundsException e) {
            String expected = "Index: " + index + ", Size: 1";
            if (!expected.equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }
}
