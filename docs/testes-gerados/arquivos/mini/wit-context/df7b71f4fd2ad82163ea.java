/*
 * @test
 * @summary Test sun.security.x509.SerialNumber.encode(DerOutputStream) method
 * @run main SerialNumberWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerOutputStream;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeBasic();
        testEncodeLargeNumber();
    }

    private static void testEncodeBasic() throws IOException {
        BigInteger num = BigInteger.valueOf(123456789L);
        SerialNumber sn = new SerialNumber(num);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        sn.encode(dos);
        dos.close();
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeLargeNumber() throws IOException {
        // Use a large BigInteger to test encode with large value
        // Not expecting OutOfMemoryError in normal test environment
        // Just verify encode completes and output is non-empty
        BigInteger largeNum = new BigInteger("1234567890123456789012345678901234567890");
        SerialNumber sn = new SerialNumber(largeNum);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        sn.encode(dos);
        dos.close();
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output for large number is empty");
        }
    }
}
