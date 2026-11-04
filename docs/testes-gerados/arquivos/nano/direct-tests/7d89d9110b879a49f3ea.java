/*
 * @test
 * @summary Test for StringUTF16.stripTrailing(byte[])
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testStripTrailingEmpty();
        testStripTrailingNoWhitespace();
        testStripTrailingWithWhitespace();
        testStripTrailingAllWhitespace();
        testStripTrailingNullReturn();
    }

    static void testStripTrailingEmpty() {
        byte[] input = new byte[0];
        String result = StringUTF16.stripTrailing(input);
        assert result.equals("") : "Expected empty string for empty input";
    }

    static void testStripTrailingNoWhitespace() {
        byte[] input = {0, 65, 0, 66, 0, 67}; // "ABC" in UTF16BE
        String result = StringUTF16.stripTrailing(input);
        assert result != null : "Result should not be null";
        assert result.equals("ABC") : "Expected 'ABC', got: " + result;
    }

    static void testStripTrailingWithWhitespace() {
        byte[] input = {0, 65, 0, 66, 0, 32, 0, 9}; // "AB \t" in UTF16BE
        String result = StringUTF16.stripTrailing(input);
        assert result != null : "Result should not be null";
        assert result.equals("AB") : "Expected 'AB', got: " + result;
    }

    static void testStripTrailingAllWhitespace() {
        byte[] input = {0, 32, 0, 9, 0, 13}; // whitespace characters
        String result = StringUTF16.stripTrailing(input);
        assert result.equals("") : "Expected empty string for all whitespace";
    }

    static void testStripTrailingNullReturn() {
        byte[] input = {0, 65, 0, 66, 0, 67}; // "ABC" in UTF16BE
        String result = StringUTF16.stripTrailing(input);
        assert result != null : "Result should not be null";
        assert result.equals("ABC") : "Expected 'ABC', got: " + result;
    }
}
