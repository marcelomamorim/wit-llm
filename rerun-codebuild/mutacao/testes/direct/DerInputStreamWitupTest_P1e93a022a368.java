/* @test    @summary Test DerInputStream.getBigInteger() with valid and invalid tags    @run main DerInputStreamWitupTest_P1e93a022a368 */

import sun.security.util.DerInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class DerInputStreamWitupTest_P1e93a022a368 {

    public static void main(String[] args) throws Exception {
        testValidBigInteger();
        testInvalidTag();
    }

    private static void testValidBigInteger() throws IOException {
        byte[] data = new byte[] {
            0x02, // Tag for Integer
            0x01, // Length
            0x05  // Value
        };
        DerInputStream dis = new DerInputStream(data);
        BigInteger result = dis.getBigInteger();
        assert result.equals(BigInteger.valueOf(5)) : "Expected BigInteger 5, got " + result;
    }

    private static void testInvalidTag() {
        byte[] data = new byte[] {
            0x03, // Tag for Bit String (invalid for getBigInteger)
            0x01, // Length
            0x05  // Value
        };
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getBigInteger();
            assert false : "Expected IOException due to invalid tag";
        } catch (IOException e) {
            assert e.getMessage().contains("DER input, Integer tag error") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
