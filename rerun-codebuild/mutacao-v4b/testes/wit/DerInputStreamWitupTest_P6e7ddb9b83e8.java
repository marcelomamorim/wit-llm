/* @test
   @summary Testa o método getBigInteger() de DerInputStream.
   @run main DerInputStreamWitupTest_P6e7ddb9b83e8
 */
import java.util.Objects;
import java.io.IOException;
import sun.security.util.DerInputStream;
import java.math.BigInteger;

public class DerInputStreamWitupTest_P6e7ddb9b83e8 {
    public static void main(String[] args) throws Exception {
        testGetBigIntegerValidTag();
        testGetBigIntegerInvalidTag();
    }

    private static void testGetBigIntegerValidTag() throws Exception {
        byte[] data = new byte[] { (byte)0x02, 0x01, 0x05 }; // tag=0x02 (INTEGER), length=1, value=5
        DerInputStream dis = new DerInputStream(data);
        BigInteger result = dis.getBigInteger();
        if (!result.equals(BigInteger.valueOf(5))) {
            throw new AssertionError("Expected BigInteger 5, got " + result);
        }
    }

    private static void testGetBigIntegerInvalidTag() throws Exception {
        byte[] data = new byte[] { (byte)0x03, 0x01, 0x05 }; // tag=0x03 (not INTEGER)
        DerInputStream dis = new DerInputStream(data);
        try {
            dis.getBigInteger();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // expected
            if (!e.getMessage().contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
