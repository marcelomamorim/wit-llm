/*
 * @test
 * @summary Test DerInputStream.getBigInteger() for valid and invalid cases
 * @run main DerInputStreamWitupTest_P4ead69b97583
 */

import sun.security.util.DerInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class DerInputStreamWitupTest_P4ead69b97583 {

    public static void main(String[] args) throws IOException {
        testValidBigInteger();
        testInvalidTagThrows();
    }

    private static void testValidBigInteger() throws IOException {
        byte[] data = new byte[] {
            0x02, 0x01, 0x05
        };
        DerInputStream dis = new DerInputStream(data);
        BigInteger result = dis.getBigInteger();
        assert result.equals(BigInteger.valueOf(5)) : "Expected BigInteger 5, got " + result;
    }

    private static void testInvalidTagThrows() {
        byte[] data = new byte[] {
            0x03, 0x01, 0x05
        };
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getBigInteger();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            assert e.getMessage().contains("DER input") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
