/*
 * @test
 * @summary Test for String.charAt(int) method to verify exception behavior.
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testCharAtWithValidIndex();
        testCharAtWithNegativeIndex();
        testCharAtWithIndexEqualToLength();
    }

    private static void testCharAtWithValidIndex() {
        String s = "abc";
        char c = s.charAt(1);
        if (c != 'b') {
            throw new AssertionError("Expected 'b', but got: " + c);
        }
    }

    private static void testCharAtWithNegativeIndex() {
        String s = "abc";
        try {
            s.charAt(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testCharAtWithIndexEqualToLength() {
        String s = "abc";
        try {
            s.charAt(s.length());
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index equal to length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
