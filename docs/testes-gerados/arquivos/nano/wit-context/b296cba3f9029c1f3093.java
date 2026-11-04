/*
 * @test
 * @summary Tests for String.substring(int beginIndex)
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testSubstringWithNegativeBeginIndex();
        testSubstringWithBeginIndexEqualToLength();
        testSubstringWithValidBeginIndex();
    }

    private static void testSubstringWithNegativeBeginIndex() {
        String s = "example";
        try {
            s.substring(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative beginIndex");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testSubstringWithBeginIndexEqualToLength() {
        String s = "example";
        int len = s.length();
        try {
            s.substring(len);
            // According to the implementation, this should return an empty string
            String result = s.substring(len);
            if (!result.equals("") && result != s) {
                throw new AssertionError("Expected empty string when beginIndex == length");
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new AssertionError("Did not expect exception when beginIndex == length", e);
        }
    }

    private static void testSubstringWithValidBeginIndex() {
        String s = "example";
        String sub = s.substring(2);
        if (!sub.equals("ample")) {
            throw new AssertionError("Expected 'ample', got: " + sub);
        }
    }
}
