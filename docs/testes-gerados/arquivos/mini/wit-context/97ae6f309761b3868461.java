/*
 * @test
 * @summary Test java.util.Collections.EmptyList.forEach method for null action argument
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Consumer;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
    }

    private static void testForEachNullAction() {
        // Collections.emptyList() returns an EmptyList instance
        // which has the forEach method under test
        java.util.List<Object> emptyList = Collections.emptyList();

        // Test that forEach throws NullPointerException when action is null
        try {
            emptyList.forEach(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test that forEach with a non-null Consumer does not throw
        Consumer<Object> consumer = new Consumer<Object>() {
            public void accept(Object o) {
                // no-op
            }
        };
        emptyList.forEach(consumer);
    }
}
