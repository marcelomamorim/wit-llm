/*
 * @test
 * @summary Test for ImmutableCollections.lastIndexOf(Object o) method.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testLastIndexOfWithNull();
        testLastIndexOfWithExistingObject();
        testLastIndexOfWithNonExistingObject();
    }

    private static void testLastIndexOfWithNull() {
        try {
            ImmutableCollections.lastIndexOf(null);
            System.out.println("FAIL: Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null argument");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testLastIndexOfWithExistingObject() {
        java.util.List<String> list = java.util.Arrays.asList("a", "b", "c", "b");
        int index = ImmutableCollections.lastIndexOf("b");
        if (index == 3) {
            System.out.println("PASS: Correct last index for existing object");
        } else {
            System.out.println("FAIL: Incorrect last index for existing object: " + index);
        }
    }

    private static void testLastIndexOfWithNonExistingObject() {
        int index = ImmutableCollections.lastIndexOf("z");
        if (index == -1) {
            System.out.println("PASS: Correctly returned -1 for non-existing object");
        } else {
            System.out.println("FAIL: Incorrect index for non-existing object: " + index);
        }
    }
}
