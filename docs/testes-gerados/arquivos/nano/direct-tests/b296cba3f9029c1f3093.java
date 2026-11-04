/*
 * @test
 * @summary Test String.substring(int beginIndex) for various cases
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testSubstringAtZero();
        testSubstringAtMiddle();
        testSubstringAtEnd();
        testSubstringWithNegativeIndex();
        testSubstringWithIndexGreaterThanLength();
    }

    static void testSubstringAtZero() {
        String original = "HelloWorld";
        String result = original.substring(0);
        if (!result.equals("HelloWorld")) {
            throw new AssertionError("Expected 'HelloWorld', got: " + result);
        }
    }

    static void testSubstringAtMiddle() {
        String original = "HelloWorld";
        String result = original.substring(5);
        if (!result.equals("World")) {
            throw new AssertionError("Expected 'World', got: " + result);
        }
    }

    static void testSubstringAtEnd() {
        String original = "HelloWorld";
        String result = original.substring(10);
        if (!result.equals("") ) {
            throw new AssertionError("Expected '', got: " + result);
        }
    }

    static void testSubstringWithNegativeIndex() {
        String original = "Hello";
        try {
            original.substring(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubstringWithIndexGreaterThanLength() {
        String original = "Hello";
        try {
            original.substring(6);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
