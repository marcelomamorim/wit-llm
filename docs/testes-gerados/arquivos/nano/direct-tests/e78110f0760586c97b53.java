/* @test    @summary Test StringBuilder.replace method for correctness and regressions    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testReplaceBasic();
        testReplaceWithEmptyString();
        testReplaceWithFullString();
        testReplaceWithInvalidIndices();
    }

    static void testReplaceBasic() {
        StringBuilder sb = new StringBuilder("Hello World");
        StringBuilder result = sb.replace(6, 11, "Java");
        assert result == sb : "replace should return the same StringBuilder instance";
        assert sb.toString().equals("Hello Java") : "Basic replace failed";
    }

    static void testReplaceWithEmptyString() {
        StringBuilder sb = new StringBuilder("Test String");
        StringBuilder result = sb.replace(0, 4, "");
        assert result == sb : "replace with empty string should return the same StringBuilder";
        assert sb.toString().equals(" String") : "Replace with empty string failed";
    }

    static void testReplaceWithFullString() {
        StringBuilder sb = new StringBuilder("Sample Text");
        StringBuilder result = sb.replace(0, sb.length(), "Replaced");
        assert result == sb : "replace with full string should return the same StringBuilder";
        assert sb.toString().equals("Replaced") : "Replace with full string failed";
    }

    static void testReplaceWithInvalidIndices() {
        StringBuilder sb = new StringBuilder("Invalid Test");
        try {
            sb.replace(-1, 5, "X");
            assert false : "Expected IndexOutOfBoundsException for negative start";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.replace(3, 2, "Y");
            assert false : "Expected IndexOutOfBoundsException for end < start";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.replace(0, 100, "Z");
            assert false : "Expected IndexOutOfBoundsException for end > length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
