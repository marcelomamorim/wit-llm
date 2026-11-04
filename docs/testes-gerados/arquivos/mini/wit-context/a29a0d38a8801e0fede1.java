/*
 * @test
 * @summary Test UnsupportedOperationException thrown by Collections$UnmodifiableList.add(E)
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testUnmodifiableListAddThrows();
    }

    private static void testUnmodifiableListAddThrows() {
        List<String> list = new ArrayList<>();
        list.add("initial");
        List<String> unmodifiable = Collections.unmodifiableList(list);
        try {
            unmodifiable.add("test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception caught, test passes
        }
    }
}
