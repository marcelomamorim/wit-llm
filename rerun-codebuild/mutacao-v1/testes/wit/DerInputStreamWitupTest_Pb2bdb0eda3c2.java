/* @test    @summary Test getBigInteger() method for DerInputStream class    @run main DerInputStreamWitupTest_Pb2bdb0eda3c2 */

import sun.security.util.DerInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class DerInputStreamWitupTest_Pb2bdb0eda3c2 {

    public static void main(String[] args) throws Exception {
        testGetBigIntegerValidTag();
        testGetBigIntegerInvalidTag();
        testGetBigIntegerBufferIOException();
    }

    private static void testGetBigIntegerValidTag() throws IOException {
        byte[] data = { (byte)0x02, 0x01, 0x05 }; // Tag=0x02 (INTEGER), length=1, value=5
        DerInputStream dis = new DerInputStream(data);
        BigInteger result = dis.getBigInteger();
        if (result == null || result.intValue() != 5) {
            throw new AssertionError("FAIL: getBigInteger with valid tag did not return expected value");
        }
    }

    private static void testGetBigIntegerInvalidTag() throws IOException {
        byte[] data = { (byte)0x03, 0x01, 0x05 }; // Tag=0x03 (not INTEGER)
        DerInputStream dis = new DerInputStream(data);
        try {
            dis.getBigInteger();
            throw new AssertionError("FAIL: getBigInteger with invalid tag did not throw IOException");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testGetBigIntegerBufferIOException() throws IOException {
        byte[] data = { (byte)0x02, 0x01 }; // Incomplete data, missing value bytes
        DerInputStream dis = new DerInputStream(data);
        try {
            dis.getBigInteger();
            throw new AssertionError("FAIL: getBigInteger with incomplete data did not throw IOException");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
