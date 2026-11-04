/*
 * @test
 * @summary Test String.copyValueOf with various valid inputs
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {
    public static void main(String[] args) {
        testCopyValueOfBasic();
        testCopyValueOfEmpty();
        testCopyValueOfFullArray();
        testCopyValueOfPartialArray();
    }

    static void testCopyValueOfBasic() {
        char[] data = {'a', 'b', 'c', 'd'};
        String result = String.copyValueOf(data, 1, 2);
        if (!"bc".equals(result)) {
            throw new AssertionError("Expected 'bc', got: " + result);
        }
    }

    static void testCopyValueOfEmpty() {
        char[] data = {'x', 'y', 'z'};
        String result = String.copyValueOf(data, 2, 0);
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string, got: " + result);
        }
    }

    static void testCopyValueOfFullArray() {
        char[] data = {'h', 'e', 'l', 'l', 'o'};
        String result = String.copyValueOf(data, 0, data.length);
        if (!"hello".equals(result)) {
            throw new AssertionError("Expected 'hello', got: " + result);
        }
    }

    static void testCopyValueOfPartialArray() {
        char[] data = {'J', 'a', 'v', 'a', '!', '!', '!'};
        String result = String.copyValueOf(data, 1, 4);
        if (!"ava!".equals(result)) {
            throw new AssertionError("Expected 'ava!', got: " + result);
        }
    }
}
