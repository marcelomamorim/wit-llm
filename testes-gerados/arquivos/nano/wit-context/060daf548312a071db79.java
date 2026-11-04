/* @test    @summary Test deserialization from InputStream for SerialNumber class    @run main SerialNumberWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import sun.security.util.DerValue;
import sun.security.x509.SerialNumber;

public class SerialNumberWitupTest {

    public static void main(String[] args) {
        testSerialNumberInputStreamValid();
        testSerialNumberInputStreamIOExceptionTagNot2();
        testSerialNumberInputStreamIOExceptionTag2AndDataAvailable();
        testSerialNumberInputStreamIOExceptionDataAvailableZero();
    }

    private static void testSerialNumberInputStreamValid() {
        try {
            BigInteger bigInt = BigInteger.valueOf(12345);
            byte[] derEncoded = encodeDerInteger(bigInt);
            InputStream in = new ByteArrayInputStream(derEncoded);
            SerialNumber sn = new SerialNumber(in);
            assert sn.getNumber().equals(bigInt) : "SerialNumber does not match";
            System.out.println("testSerialNumberInputStreamValid passed");
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid test", e);
        }
    }

    private static void testSerialNumberInputStreamIOExceptionTagNot2() {
        try {
            byte[] derEncoded = encodeDerInteger(BigInteger.valueOf(1));
            // Manipulate the first byte to set tag != 2 (e.g., 0x01)
            derEncoded[0] = 0x01;
            InputStream in = new ByteArrayInputStream(derEncoded);
            new SerialNumber(in);
            throw new AssertionError("Expected IOException for tag != 2");
        } catch (IOException e) {
            System.out.println("testSerialNumberInputStreamIOExceptionTagNot2 passed");
        }
    }

    private static void testSerialNumberInputStreamIOExceptionTag2AndDataAvailable() {
        try {
            byte[] derEncoded = encodeDerInteger(BigInteger.valueOf(1));
            // Set tag to 2
            derEncoded[0] = 0x02;
            InputStream in = new ByteArrayInputStream(derEncoded);
            new SerialNumber(in);
            throw new AssertionError("Expected IOException for tag == 2 and data available");
        } catch (IOException e) {
            System.out.println("testSerialNumberInputStreamIOExceptionTag2AndDataAvailable passed");
        }
    }

    private static void testSerialNumberInputStreamIOExceptionDataAvailableZero() {
        try {
            byte[] derEncoded = encodeDerInteger(BigInteger.valueOf(1));
            // Set tag to 2
            derEncoded[0] = 0x02;
            // Create InputStream with no data after the tag
            InputStream in = new ByteArrayInputStream(derEncoded) {
                @Override
                public int available() {
                    return 0;
                }
            };
            new SerialNumber(in);
            throw new AssertionError("Expected IOException for data.available() == 0 and tag == 2 and count >= pos");
        } catch (IOException e) {
            System.out.println("testSerialNumberInputStreamIOExceptionDataAvailableZero passed");
        }
    }

    private static byte[] encodeDerInteger(BigInteger value) {
        // Basic DER encoding for INTEGER: 0x02, length, value bytes
        byte[] valueBytes = value.toByteArray();
        byte[] der = new byte[2 + valueBytes.length];
        der[0] = 0x02; // INTEGER tag
        der[1] = (byte) valueBytes.length; // length
        System.arraycopy(valueBytes, 0, der, 2, valueBytes.length);
        return der;
    }
}
