/* @test    @summary Test StringBuffer.replace method for correct behavior and edge cases    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testReplaceBasic();
        testReplaceWithEmptyString();
        testReplaceWithFullBuffer();
        testReplaceWithInvalidIndices();
    }

    static void testReplaceBasic() {
        StringBuffer sb = new StringBuffer("Hello World");
        sb.replace(0, 5, "Hi");
        assert "Hi World".equals(sb.toString()) : "Basic replace failed";
    }

    static void testReplaceWithEmptyString() {
        StringBuffer sb = new StringBuffer("Sample Text");
        sb.replace(0, 6, "");
        assert " Text".equals(sb.toString()) : "Replace with empty string failed";
    }

    static void testReplaceWithFullBuffer() {
        StringBuffer sb = new StringBuffer("Data");
        sb.replace(0, sb.length(), "NewData");
        assert "NewData".equals(sb.toString()) : "Replace entire buffer failed";
    }

    static void testReplaceWithInvalidIndices() {
        StringBuffer sb = new StringBuffer("Test");
        try {
            sb.replace(-1, 2, "X");
            assert false : "Expected IndexOutOfBoundsException for negative start";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.replace(1, 10, "Y");
            assert false : "Expected IndexOutOfBoundsException for end > length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.replace(3, 2, "Z");
            assert false : "Expected IndexOutOfBoundsException for start > end";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
