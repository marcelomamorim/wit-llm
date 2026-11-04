/* @test    @summary Test encoding of SerialNumber class in sun.security.x509    @run main SerialNumberWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) throws IOException {
        testEncodeWithPositiveNumber();
        testEncodeWithZero();
        testEncodeWithLargeNumber();
    }

    static void testEncodeWithPositiveNumber() throws IOException {
        BigInteger value = BigInteger.valueOf(12345);
        SerialNumber sn = new SerialNumber(value);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        sn.encode(dos);
        byte[] encoded = baos.toByteArray();
        // Check that the encoded bytes contain the expected DER encoding for the integer
        if (encoded.length == 0 || encoded[0] != 2) {
            throw new AssertionError("Encoding does not start with INTEGER tag");
        }
        System.out.println("testEncodeWithPositiveNumber passed.");
    }

    static void testEncodeWithZero() throws IOException {
        BigInteger value = BigInteger.ZERO;
        SerialNumber sn = new SerialNumber(value);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        sn.encode(dos);
        byte[] encoded = baos.toByteArray();
        if (encoded.length == 0 || encoded[0] != 2) {
            throw new AssertionError("Encoding does not start with INTEGER tag");
        }
        System.out.println("testEncodeWithZero passed.");
    }

    static void testEncodeWithLargeNumber() throws IOException {
        BigInteger largeNumber = new BigInteger("123456789012345678901234567890");
        SerialNumber sn = new SerialNumber(largeNumber);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        sn.encode(dos);
        byte[] encoded = baos.toByteArray();
        if (encoded.length == 0 || encoded[0] != 2) {
            throw new AssertionError("Encoding does not start with INTEGER tag");
        }
        System.out.println("testEncodeWithLargeNumber passed.");
    }
}
