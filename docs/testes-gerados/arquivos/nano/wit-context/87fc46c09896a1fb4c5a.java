/*
 * @test
 * @summary Test for ImmutableCollections.containsKey(Object o)
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testContainsKeyWithNull();
        testContainsKeyWithNonNull();
    }

    private static void testContainsKeyWithNull() {
        ImmutableCollections instance = new ImmutableCollections();
        try {
            instance.containsKey(null);
            System.out.println("Test failed: Expected NullPointerException for null argument.");
        } catch (NullPointerException e) {
            System.out.println("Test passed: NullPointerException thrown as expected for null argument.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception type: " + t);
        }
    }

    private static void testContainsKeyWithNonNull() {
        ImmutableCollections instance = new ImmutableCollections();
        Object testObject = new Object();
        try {
            boolean result = instance.containsKey(testObject);
            System.out.println("containsKey with non-null argument returned: " + result);
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
