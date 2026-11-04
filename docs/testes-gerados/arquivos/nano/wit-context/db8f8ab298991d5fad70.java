/*
 * @test
 * @summary Test for ImmutableCollections.removeIf method to verify exception throwing behavior.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.Predicate;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfThrowsUOE();
    }

    private static void testRemoveIfThrowsUOE() {
        ImmutableCollections.AbstractImmutableCollection<?> collection = new ImmutableCollections.AbstractImmutableCollection<Object>() {
            @Override
            public int size() {
                return 0;
            }
        };
        Predicate<Object> predicate = obj -> true;
        try {
            collection.removeIf(predicate);
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
