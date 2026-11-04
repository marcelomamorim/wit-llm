/*
 * @test
 * @summary Test sun.security.util.DerValue.getT61String() method
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetT61StringWithCorrectTag();
        testGetT61StringWithIncorrectTag();
    }

    private static void testGetT61StringWithCorrectTag() throws Exception {
        // tag_T61String is 0x14 (decimal 20) as per DER universal tags
        final byte tag_T61String = 0x14;
        // Construct DerValue with tag_T61String and known ISO-8859-1 bytes
        byte[] data = {(byte)0x48, (byte)0x65, (byte)0x6C, (byte)0x6C, (byte)0x6F}; // "Hello"
        DerValue dv = new DerValue(tag_T61String, data);

        String result = dv.getT61String();
        if (!"Hello".equals(result)) {
            throw new RuntimeException("Expected 'Hello' but got '" + result + "'");
        }
    }

    private static void testGetT61StringWithIncorrectTag() {
        // Use a tag different from tag_T61String (0x14), e.g. tag_Integer (0x02)
        final byte tag_Integer = 0x02;
        byte[] data = {(byte)0x01};
        DerValue dv = new DerValue(tag_Integer, data);

        try {
            dv.getT61String();
            throw new RuntimeException("Expected IOException for wrong tag but none thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not T61")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
