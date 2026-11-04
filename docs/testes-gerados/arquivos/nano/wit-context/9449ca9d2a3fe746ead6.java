/* @test    @summary Test for String.codePointBefore(int) exception paths    @run main StringWitupTest */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testCodePointBeforeThrows();
        testCodePointBeforeValid();
    }

    private static void testCodePointBeforeThrows() {
        String str = "abc";
        int length = str.length();
        // Test with index less than 1 (e.g., 0)
        try {
            str.codePointBefore(0);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for index=0");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
        // Test with index greater than length
        try {
            str.codePointBefore(length + 1);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for index=" + (length + 1));
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testCodePointBeforeValid() {
        String str = "abc";
        // Valid index: 1 (before 'b')
        int cp = str.codePointBefore(1);
        if (cp != 'a') {
            System.out.println("Unexpected code point: " + cp);
        }
        // Valid index: 2 (before 'c')
        cp = str.codePointBefore(2);
        if (cp != 'b') {
            System.out.println("Unexpected code point: " + cp);
        }
    }
}
