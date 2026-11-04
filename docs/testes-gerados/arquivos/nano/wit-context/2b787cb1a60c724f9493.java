/*
 * @test
 * @summary Test for ImmutableCollections.contains(Object o)
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithNonNull();
    }

    static void testContainsWithNull() {
        ImmutableCollections coll = new ImmutableCollections();
        boolean result = false;
        try {
            result = coll.contains(null);
            // Expecting NullPointerException as per expath
            System.out.println("Test contains(null): Unexpected success, result=" + result);
        } catch (NullPointerException e) {
            System.out.println("Test contains(null): Caught expected NullPointerException");
        } catch (Throwable t) {
            System.out.println("Test contains(null): Caught unexpected exception: " + t);
        }
    }

    static void testContainsWithNonNull() {
        ImmutableCollections coll = new ImmutableCollections();
        boolean result = false;
        try {
            result = coll.contains("test");
            System.out.println("Test contains('test'): result=" + result);
        } catch (Throwable t) {
            System.out.println("Test contains('test'): Unexpected exception: " + t);
        }
    }
}
