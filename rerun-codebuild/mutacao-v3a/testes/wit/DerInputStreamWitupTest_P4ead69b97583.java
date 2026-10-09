/* @test    @summary Tests for sun.security.util.DerInputStream.getBigInteger()    @run main DerInputStreamWitupTest_P4ead69b97583 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest_P4ead69b97583 {

    public static void main(String[] args) throws Exception {
        testGetBigIntegerWithValidIntegerTag();
        testGetBigIntegerWithInvalidTag();
        testGetBigIntegerWithEmptyBuffer();
    }

    private static void testGetBigIntegerWithValidIntegerTag() {
        byte[] data = { (byte)0x02, 0x01, 0x05 };
        try {
            DerInputStream dis = new DerInputStream(data);
            BigInteger result = dis.getBigInteger();
            if (result == null || !result.equals(BigInteger.valueOf(5))) {
                throw new AssertionError("Expected BigInteger value 5, but got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static void testGetBigIntegerWithInvalidTag() {
        byte[] data = { (byte)0x03, 0x01, 0x05 };
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getBigInteger();
            throw new AssertionError("Expected IOException due to invalid tag, but none thrown.");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testGetBigIntegerWithEmptyBuffer() {
        byte[] data = {};
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getBigInteger();
            throw new AssertionError("Expected IOException due to empty buffer, but none thrown.");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
