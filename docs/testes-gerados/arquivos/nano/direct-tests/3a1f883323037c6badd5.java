/*
 * @test    @summary Test StringLatin1.inflate method for correct byte array inflation
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;
import java.util.Arrays;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testInflateWithEmptyArray();
        testInflateWithSingleByte();
        testInflateWithMultipleBytes();
        testInflateWithNullBytes();
    }

    static void testInflateWithEmptyArray() {
        byte[] input = new byte[0];
        byte[] result = StringLatin1.inflate(input, 0, 0);
        assert result != null : "Result should not be null";
        assert result.length == 0 : "Result length should be 0";
    }

    static void testInflateWithSingleByte() {
        byte[] input = { (byte) 0x41 };
        byte[] result = StringLatin1.inflate(input, 0, 1);
        assert result != null : "Result should not be null";
        assert result.length == 1 : "Result length should be 1";
        assert result[0] == (byte) 0x41 : "First byte should be 0x41";
    }

    static void testInflateWithMultipleBytes() {
        byte[] input = { (byte) 0x41, (byte) 0x42, (byte) 0x43 };
        byte[] result = StringLatin1.inflate(input, 0, 3);
        assert result != null : "Result should not be null";
        assert result.length == 3 : "Result length should be 3";
        assert result[0] == (byte) 0x41 : "First byte should be 0x41";
        assert result[1] == (byte) 0x42 : "Second byte should be 0x42";
        assert result[2] == (byte) 0x43 : "Third byte should be 0x43";
    }

    static void testInflateWithNullBytes() {
        byte[] input = null;
        try {
            StringLatin1.inflate(input, 0, 0);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
