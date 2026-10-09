/* @test    @summary Test DerInputStream.getInteger() method for correct behavior and exception handling    @run main DerInputStreamWitupTest_P221054baf086 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest_P221054baf086 {

    public static void main(String[] args) throws Exception {
        testValidInteger();
        testInvalidTag();
        testBufferWithIncorrectTag();
        System.out.println("All tests passed.");
    }

    private static void testValidInteger() throws IOException {
        byte[] data = new byte[] {
            (byte)0x02, // Tag for Integer
            0x01, // Length
            0x05 // Value
        };
        DerInputStream dis = new DerInputStream(data);
        int value = dis.getInteger();
        if (value != 5) {
            throw new AssertionError("Expected 5, got " + value);
        }
    }

    private static void testInvalidTag() {
        byte[] data = new byte[] {
            (byte)0x03, // Wrong tag
            0x01, // Length
            0x05 // Value
        };
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getInteger();
            throw new AssertionError("Expected IOException due to wrong tag");
        } catch (IOException e) {
            if (!e.getMessage().contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testBufferWithIncorrectTag() {
        byte[] data = new byte[] {
            (byte)0x02, // Tag for Integer
            0x01, // Length
            0x05 // Value
        };
        try {
            // Manipulate buffer to simulate incorrect tag read
            DerInputStream dis = new DerInputStream(data) {
                @Override
                public int getInteger() throws IOException {
                    if (buffer.read() != (byte)0x02) {
                        throw new IOException("DER input, Integer tag error");
                    }
                    return buffer.getInteger(getDefiniteLength(buffer));
                }
            };
            dis.getInteger();
            // Should not reach here
            throw new AssertionError("Expected IOException due to incorrect tag read");
        } catch (IOException e) {
            if (!e.getMessage().contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
