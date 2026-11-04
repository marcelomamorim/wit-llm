/* @test    @summary Test StringBuffer.delete(int, int) method for correctness and exception handling    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testDeleteBasic();
        testDeleteFullRange();
        testDeleteEmptyRange();
        testDeleteInvalidRange();
    }

    static void testDeleteBasic() {
        StringBuffer sb = new StringBuffer("Hello, World!");
        sb.delete(5, 7); // removes ", "
        assert sb.toString().equals("HelloWorld!") : "Failed basic delete";
    }

    static void testDeleteFullRange() {
        StringBuffer sb = new StringBuffer("Test");
        sb.delete(0, sb.length()); // deletes entire content
        assert sb.toString().equals("") : "Failed full range delete";
    }

    static void testDeleteEmptyRange() {
        StringBuffer sb = new StringBuffer("Sample");
        sb.delete(3, 3); // no change
        assert sb.toString().equals("Sample") : "Failed empty range delete";
    }

    static void testDeleteInvalidRange() {
        StringBuffer sb = new StringBuffer("ErrorTest");
        try {
            sb.delete(-1, 3); // start negative
            assert false : "Expected StringIndexOutOfBoundsException for negative start";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.delete(2, 20); // end beyond length
            assert false : "Expected StringIndexOutOfBoundsException for end beyond length";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.delete(5, 3); // start > end
            assert false : "Expected StringIndexOutOfBoundsException for start > end";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
