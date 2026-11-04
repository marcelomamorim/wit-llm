/* @test    @summary Test for ImmutableCollections.merge method to verify exception throwing behavior    @run main ImmutableCollectionsWitupTest */

import java.util.*;
import java.util.function.BiFunction;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testMergeThrowsUOE();
    }

    public static void testMergeThrowsUOE() {
        ImmutableCollections instance = new ImmutableCollections();
        BiFunction<Object, Object, Object> rf = (a, b) -> a;
        try {
            Object result = instance.merge(null, null, rf);
            // Expecting UnsupportedOperationException
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
            System.out.println("UnsupportedOperationException correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
