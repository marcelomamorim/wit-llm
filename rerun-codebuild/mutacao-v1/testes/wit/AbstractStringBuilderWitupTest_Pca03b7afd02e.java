/* @test    @summary Test for AbstractStringBuilder.codePointBefore(int index) exception behavior    @run main AbstractStringBuilderWitupTest_Pca03b7afd02e */

import java.lang.StringIndexOutOfBoundsException;

public class AbstractStringBuilderWitupTest_Pca03b7afd02e {

    public static void main(String[] args) {
        testCodePointBeforeWithInvalidIndex();
    }

    private static void testCodePointBeforeWithInvalidIndex() {
        java.lang.AbstractStringBuilder builder = new java.lang.AbstractStringBuilder(10) {
            // Implement abstract methods if necessary, or use a concrete subclass if available
        };
        // Initialize builder with some content to set 'count'
        // Since AbstractStringBuilder is abstract, use a concrete subclass for testing
        // For simplicity, use StringBuilder as a stand-in, assuming similar behavior
        StringBuilder sb = new StringBuilder();
        sb.append("abc");
        int length = sb.length();
        try {
            // index less than 0
            sb.codePointBefore(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            // index equal to length
            sb.codePointBefore(length);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index " + length);
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            // index greater than length
            sb.codePointBefore(length + 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index " + (length + 1));
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
        System.out.println("PASS: codePointBefore with invalid indices")
    }
}
