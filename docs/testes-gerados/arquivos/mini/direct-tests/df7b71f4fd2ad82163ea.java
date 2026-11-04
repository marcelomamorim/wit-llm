/*
 * @test
 * @summary Test sun.security.x509.SerialNumber.encode(DerOutputStream) method
 * @run main sun.security.x509.SerialNumberWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerOutputStream;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {
    public static void main(String[] args) throws IOException {
        testEncodeWithBigIntegerConstructor();
        testEncodeWithIntConstructor();
    }

    private static void testEncodeWithBigIntegerConstructor() throws IOException {
        BigInteger bigInt = new BigInteger("123456789");
        SerialNumber sn = new SerialNumber(bigInt);
        DerOutputStream out = new DerOutputStream();
        sn.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoding failed: output is empty");
        }
    }

    private static void testEncodeWithIntConstructor() throws IOException {
        int num = 987654321;
        SerialNumber sn = new SerialNumber(num);
        DerOutputStream out = new DerOutputStream();
        sn.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoding failed: output is empty");
        }
    }
}
