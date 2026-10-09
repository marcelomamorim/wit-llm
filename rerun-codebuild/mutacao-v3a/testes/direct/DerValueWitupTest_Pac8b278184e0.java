/* @test    @summary Test DerValue.getUTF8String() method for correct behavior and exception handling    @run main DerValueWitupTest_Pac8b278184e0 */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_Pac8b278184e0 {

    public static void main(String[] args) {
        testValidUTF8String();
        testInvalidTagThrowsIOException();
    }

    static void testValidUTF8String() {
        try {
            byte[] utf8Bytes = "hello".getBytes("UTF8");
            // Tag for UTF8String is 0x0C, as per DER encoding for UTF8String
            byte tagUTF8 = 0x0C;
            byte[] encoded = new byte[2 + utf8Bytes.length];
            encoded[0] = tagUTF8;
            encoded[1] = (byte) utf8Bytes.length;
            System.arraycopy(utf8Bytes, 0, encoded, 2, utf8Bytes.length);
            DerValue derValue = new DerValue(encoded);
            String result = derValue.getUTF8String();
            assert "hello".equals(result) : "Expected 'hello', got '" + result + "'";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testValidUTF8String", e);
        }
    }

    static void testInvalidTagThrowsIOException() {
        try {
            byte[] data = {0x02, 0x01, 0x01}; // Tag for INTEGER
            DerValue derValue = new DerValue(data);
            derValue.getUTF8String();
            throw new AssertionError("Expected IOException for invalid tag, but none was thrown.");
        } catch (IOException e) {
            String expectedMsg = "DerValue.getUTF8String, not UTF-8 " + derValue.tag;
            assert e.getMessage().equals("DerValue.getUTF8String, not UTF-8 " + derValue.tag) : "Unexpected exception message: " + e.getMessage();
        }
    }
}
