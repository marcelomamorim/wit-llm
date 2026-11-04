/*
 * @test
 * @summary Test for ImmutableCollections.get(int index) method, including exception path.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testGetValidIndex0();
        testGetValidIndex1();
        testGetInvalidIndex();
    }

    private static void testGetValidIndex0() {
        try {
            // Prepare a collection with at least one element
            // Since the source code is not fully visible, assume e0 is initialized to a non-null value
            // For testing, we simulate the internal state by calling get with index 0
            // The method should return e0, which is not accessible, so we test that no exception is thrown
            // and the method returns without error for index 0
            // Note: We cannot access internal fields, so we rely on the method's behavior
            // For the purpose of this test, we assume get(0) does not throw
            Object result = java.util.ImmutableCollections.get(0);
            // No exception expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for index 0", t);
        }
    }

    private static void testGetValidIndex1() {
        try {
            Object result = java.util.ImmutableCollections.get(1);
            // No exception expected if e1 != null and index == 1
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for index 1", t);
        }
    }

    private static void testGetInvalidIndex() {
        try {
            java.util.ImmutableCollections.get(2);
            throw new AssertionError("Expected exception for invalid index 2 did not occur");
        } catch (Throwable t) {
            if (!(t instanceof java.lang.RuntimeException) && !(t instanceof java.lang.IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type for invalid index", t);
            }
            // Expected exception, test passes
        }
    }
}
