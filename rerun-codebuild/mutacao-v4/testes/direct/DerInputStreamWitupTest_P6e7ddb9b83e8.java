/* @test
   @summary Tests getBigInteger() method of DerInputStream for correct behavior.
   @run main DerInputStreamWitupTest_P6e7ddb9b83e8
 */
import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerInputStream;
public class DerInputStreamWitupTest_P6e7ddb9b83e8 {
    public static void main(String[] args) throws Exception {
        testValidBigInteger();
        testInvalidTagThrowsIOException();
    }
    private static void testValidBigInteger() throws IOException {
        byte[] data = new byte[] { (byte)0x02, 0x01, 0x05 };
        DerInputStream dis = new DerInputStream(data);
        BigInteger result = dis.getBigInteger();
        if (!result.equals(BigInteger.valueOf(5))) {
            throw new AssertionError("Expected BigInteger 5, got " + result);
        }
    }
    private static void testInvalidTagThrowsIOException() {
        byte[] data = new byte[] { (byte)0x03, 0x01, 0x05 };
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getBigInteger();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            if (!e.getMessage().contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
