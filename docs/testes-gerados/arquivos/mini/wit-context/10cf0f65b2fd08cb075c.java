/*
 * @test
 * @summary Test java.util.Collections.SingletonList.get(int) for correct index bounds
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a SingletonList via Collections.singletonList
        List<String> singleton = Collections.singletonList("element");

        // Test get(0) returns the element
        String e = singleton.get(0);
        if (!"element".equals(e)) {
            throw new AssertionError("Expected element at index 0");
        }

        // Test get with index != 0 throws IndexOutOfBoundsException
        try {
            singleton.get(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 1");
        } catch (IndexOutOfBoundsException ex) {
            // Expected
        }

        try {
            singleton.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException ex) {
            // Expected
        }
    }
}
