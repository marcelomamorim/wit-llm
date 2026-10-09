/* @test    @summary Test DerInputStream.getBigInteger() method for correct behavior and exception handling    @run main DerInputStreamWitupTest_Pb2bdb0eda3c2 */

import sun.security.util.DerInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class DerInputStreamWitupTest_Pb2bdb0eda3c2 {

    public static void main(String[] args) throws Exception {
        testValidBigInteger();
        testInvalidTagThrows();
        testBufferWithIncorrectTagThrows();
    }

    private static void testValidBigInteger() throws Exception {
        byte[] data = new byte[] {
            0x02, // INTEGER tag
            0x01, // length
            0x05  // value
        };
        DerInputStream dis = new DerInputStream(data);
        BigInteger result = dis.getBigInteger();
        if (!result.equals(BigInteger.valueOf(5))) {
            throw new AssertionError("Expected BigInteger 5, got " + result);
        }
    }

    private static void testInvalidTagThrows() {
        byte[] data = new byte[] {
            0x03, // BIT STRING tag, not INTEGER
            0x01, // length
            0x05  // value
        };
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getBigInteger();
            throw new AssertionError("Expected IOException for invalid tag, but none was thrown");
        } catch (IOException e) {
            if (!e.getMessage().contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testBufferWithIncorrectTagThrows() {
        byte[] data = new byte[] {
            0x02, // INTEGER tag
            0x01, // length
            0x05  // value
        };
        try {
            DerInputStream dis = new DerInputStream(data);
            // Manually override the buffer's read method to simulate wrong tag
            // Since buffer is private, simulate by creating a subclass or reflection is needed.
            // But as per constraints, we avoid reflection; instead, simulate by creating a buffer with wrong tag.
            // So, create a buffer with wrong tag at the start.
            byte[] wrongData = new byte[] {
                0x03, // wrong tag
                0x01,
                0x05
            };
            DerInputStream disWrong = new DerInputStream(wrongData);
            disWrong.getBigInteger();
            throw new AssertionError("Expected IOException for wrong tag, but none was thrown");
        } catch (IOException e) {
            if (!e.getMessage().contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
