/*
 * @test
 * @summary Verifies that the index-out-of-bounds get path of the empty list returned by Collections.emptyList() throws with the expected message
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testGetOnEmptyListThrowsIndexOutOfBounds();
        testGetWithNegativeIndexThrowsIndexOutOfBounds();
        testGetMessageContainsIndex();
        testEmptyListSizeRemainsZeroAfterFailedGet();
    }

    static void testGetOnEmptyListThrowsIndexOutOfBounds() {
        List<Object> list = Collections.emptyList();
        boolean thrown = false;
        try {
            list.get(0);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for get(0) on empty list");
        }
    }

    static void testGetWithNegativeIndexThrowsIndexOutOfBounds() {
        List<Object> list = Collections.emptyList();
        boolean thrown = false;
        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for get(-1) on empty list");
        }
    }

    static void testGetMessageContainsIndex() {
        List<Object> list = Collections.emptyList();
        try {
            list.get(5);
            throw new AssertionError("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Index: 5")) {
                throw new AssertionError("Unexpected message: " + msg);
            }
        }
    }

    static void testEmptyListSizeRemainsZeroAfterFailedGet() {
        List<Object> list = Collections.emptyList();
        try {
            list.get(0);
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        if (list.size() != 0) {
            throw new AssertionError("Size changed after failed get: " + list.size());
        }
    }
}
