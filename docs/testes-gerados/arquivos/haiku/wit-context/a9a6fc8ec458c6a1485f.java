/*
 * @test
 * @summary Verifies that get(int) on the list returned by Collections.emptyList() always throws IndexOutOfBoundsException with the message "Index: <index>"
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> list = Collections.emptyList();
        if (!list.isEmpty()) {
            throw new RuntimeException("emptyList() is not empty");
        }
        check(list, 0);
        check(list, 1);
        check(list, -1);
        check(list, Integer.MAX_VALUE);
    }

    static void check(List<String> list, int index) {
        try {
            list.get(index);
            throw new RuntimeException("No IndexOutOfBoundsException for index " + index);
        } catch (IndexOutOfBoundsException e) {
            String expected = "Index: " + index;
            if (!expected.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
