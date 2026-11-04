/* @test    @summary Test for Collections.retainAll with null argument to verify NullPointerException is thrown    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNull();
    }

    public static void testRetainAllWithNull() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            Collections.retainAll(list, null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }
}
