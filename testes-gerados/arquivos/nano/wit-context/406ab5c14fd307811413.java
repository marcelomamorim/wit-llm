/* @test    @summary Test for UnicodeSet(String pattern) constructor and exception paths    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testConstructorWithPattern("[:age=3.2:]", true); // Expect UnsupportedOperationException
        testConstructorWithPattern("[a-z]"); // Expect no exception
    }

    private static void testConstructorWithPattern(String pattern) {
        testConstructorWithPattern(pattern, false);
    }

    private static void testConstructorWithPattern(String pattern, boolean expectException) {
        try {
            UnicodeSet uset = new UnicodeSet(pattern);
            if (expectException) {
                System.out.println("FAIL: Expected exception for pattern: " + pattern);
            } else {
                System.out.println("PASS: Successfully created UnicodeSet with pattern: " + pattern);
            }
        } catch (UnsupportedOperationException e) {
            if (expectException) {
                System.out.println("PASS: Caught expected UnsupportedOperationException for pattern: " + pattern);
            } else {
                System.out.println("FAIL: Unexpected UnsupportedOperationException for pattern: " + pattern);
            }
        } catch (IllegalStateException e) {
            System.out.println("FAIL: Caught IllegalStateException for pattern: " + pattern + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception for pattern: " + pattern + ": " + e);
        }
    }
}
