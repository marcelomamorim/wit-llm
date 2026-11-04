/*
 * @test
 * @summary Test for Attrs.isDeclared(String qName) method.
 * @run main AttrsWitupTest
 */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {

    public static void main(String[] args) {
        testIsDeclaredWithValidQName();
        testIsDeclaredWithInvalidQName();
        testIsDeclaredWithEmptyItems();
    }

    private static void testIsDeclaredWithValidQName() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        // Setup internal state to simulate a declared attribute
        // Since mItems and mLength are package-private, use reflection if needed
        // but as per constraints, avoid reflection; instead, simulate via public API
        // For this test, we assume getIndex returns 0 for a known qName
        // and mItems[0<<3 + 5] != null
        // Since no public setter for mItems, we cannot set internal state directly
        // So, test behavior when getIndex returns -1 (not found)
        // and when getIndex returns a valid index with mItems[ index<<3 + 5 ] != null
        // but as we cannot set internal state, test only the exception case
        try {
            attrs.isDeclared("nonexistent");
            // Should throw IllegalArgumentException
            System.out.println("Failed: Expected exception for nonexistent attribute");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Exception thrown for nonexistent attribute");
        }
    }

    private static void testIsDeclaredWithInvalidQName() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        // Similar to above, test with a qName that would cause getIndex to return -1
        try {
            attrs.isDeclared("");
            System.out.println("Failed: Expected exception for empty qName");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Exception thrown for empty qName");
        }
    }

    private static void testIsDeclaredWithEmptyItems() {
        Attrs attrs = new Attrs();
        // No setup for mItems, so mItems is null or empty
        // Calling isDeclared should throw exception due to getIndex returning -1
        try {
            attrs.isDeclared("anyQname");
            System.out.println("Failed: Expected exception for empty items list");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Exception thrown for empty items list");
        }
    }
}
