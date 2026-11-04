/* @test    @summary Test for sun.security.x509.SerialNumber.encode(DerOutputStream out) method.
 * @run main SerialNumberWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerOutputStream;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) {
        testEncodeWithValidSerialNumber();
        testEncodeWithLargeSerialNumber();
    }

    private static void testEncodeWithValidSerialNumber() {
        try {
            BigInteger bigInt = BigInteger.valueOf(12345);
            SerialNumber serialNumber = new SerialNumber(bigInt);
            DerOutputStream out = new DerOutputStream();
            serialNumber.encode(out);
            byte[] encodedBytes = out.toByteArray();
            if (encodedBytes == null || encodedBytes.length == 0) {
                throw new AssertionError("Encoding produced empty byte array");
            }
            System.out.println("testEncodeWithValidSerialNumber passed.");
        } catch (IOException e) {
            throw new AssertionError("IOException during testEncodeWithValidSerialNumber", e);
        }
    }

    private static void testEncodeWithLargeSerialNumber() {
        try {
            BigInteger largeBigInt = new BigInteger("123456789012345678901234567890");
            SerialNumber serialNumber = new SerialNumber(largeBigInt);
            DerOutputStream out = new DerOutputStream();
            serialNumber.encode(out);
            byte[] encodedBytes = out.toByteArray();
            if (encodedBytes == null || encodedBytes.length == 0) {
                throw new AssertionError("Encoding produced empty byte array for large number");
            }
            System.out.println("testEncodeWithLargeSerialNumber passed.");
        } catch (IOException e) {
            throw new AssertionError("IOException during testEncodeWithLargeSerialNumber", e);
        }
    }
}
