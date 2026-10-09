/* @test    @summary Test for DerInputStream.getInteger() method to cover normal and exception paths    @run main DerInputStreamWitupTest_P76fa5ff76f42 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest_P76fa5ff76f42 {

    public static void main(String[] args) throws Exception {
        testGetIntegerNormal();
        testGetIntegerThrowsIOException();
    }

    public static void testGetIntegerNormal() {
        byte[] data = new byte[] {
            0x02, // DER tag for Integer
            0x01, // length
            0x05  // value
        };
        try {
            DerInputStream dis = new DerInputStream(data);
            int value = dis.getInteger();
            if (value != 5) {
                throw new AssertionError("Expected value 5, got " + value);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    public static void testGetIntegerThrowsIOException() {
        // Data with incorrect tag to trigger IOException
        byte[] invalidData = new byte[] {
            0x03, // Not an Integer tag
            0x01, // length
            0x05  // value
        };
        try {
            DerInputStream dis = new DerInputStream(invalidData);
            dis.getInteger();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
