/* @test    @summary Test DerValue.getUTF8String() for normal and exceptional behavior    @run main DerValueWitupTest_Pac8b278184e0 */

import java.io.*;
import sun.security.util.DerValue;

public class DerValueWitupTest_Pac8b278184e0 {

    public static void main(String[] args) {
        testGetUTF8String_Normal();
        testGetUTF8String_Exception();
    }

    static void testGetUTF8String_Normal() {
        try {
            byte[] utf8Bytes = "hello".getBytes("UTF8");
            // tag for UTF8String is 12 (0x0C)
            byte tagUtf8 = 0x0C;
            DerValue dv = new DerValue(tagUtf8, utf8Bytes);
            String result = dv.getUTF8String();
            if (!"hello".equals(result)) {
                throw new AssertionError("Expected 'hello', but got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in normal case", e);
        }
    }

    static void testGetUTF8String_Exception() {
        try {
            // tag != 12, e.g., tag = 0x01 (BOOLEAN)
            byte[] data = {0x01, 0x01, 0x00};
            DerValue dv = new DerValue((byte)0x01, data);
            dv.getUTF8String();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.startsWith("DerValue.getUTF8String, not UTF-8")) {
                throw new AssertionError("Unexpected IOException message: " + msg, e);
            }
        }
    }
}
