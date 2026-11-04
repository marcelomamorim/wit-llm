/*
 * @test
 * @summary Test String.charAt(int index) method for various valid and invalid indices.
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testCharAtValidIndex();
        testCharAtNegativeIndex();
        testCharAtIndexEqualToLength();
        testCharAtIndexGreaterThanLength();
    }

    static void testCharAtValidIndex() {
        String s = "abc";
        char c0 = s.charAt(0);
        char c1 = s.charAt(1);
        char c2 = s.charAt(2);
        assert c0 == 'a' : "Expected 'a' at index 0, got " + c0;
        assert c1 == 'b' : "Expected 'b' at index 1, got " + c1;
        assert c2 == 'c' : "Expected 'c' at index 2, got " + c2;
    }

    static void testCharAtNegativeIndex() {
        String s = "abc";
        try {
            s.charAt(-1);
            assert false : "Expected StringIndexOutOfBoundsException for negative index";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCharAtIndexEqualToLength() {
        String s = "abc";
        try {
            s.charAt(s.length());
            assert false : "Expected StringIndexOutOfBoundsException for index equal to length";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCharAtIndexGreaterThanLength() {
        String s = "abc";
        try {
            s.charAt(s.length() + 1);
            assert false : "Expected StringIndexOutOfBoundsException for index greater than length";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
