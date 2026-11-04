/*
 * @test
 * @summary Test SerialNumber constructor with InputStream
 * @run main SerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.SerialNumber;
import sun.security.util.DerOutputStream;

public class SerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testValidSerialNumberInputStream();
        testSerialNumberInputStreamEncodeDecode();
    }

    private static void testValidSerialNumberInputStream() throws IOException {
        // Prepare a DER encoding of an integer 12345
        DerOutputStream out = new DerOutputStream();
        out.putInteger(BigInteger.valueOf(12345));
        byte[] encoded = out.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(encoded);
        SerialNumber sn = new SerialNumber(bais);

        if (!sn.getNumber().equals(BigInteger.valueOf(12345))) {
            throw new RuntimeException("SerialNumber value mismatch: expected 12345 but got " + sn.getNumber());
        }

        String str = sn.toString();
        if (str == null || !str.contains("3039")) { // 0x3039 is hex for 12345
            throw new RuntimeException("toString() does not contain expected hex representation: " + str);
        }
    }

    private static void testSerialNumberInputStreamEncodeDecode() throws IOException {
        // Encode a SerialNumber and decode it back
        BigInteger val = new BigInteger("987654321");
        DerOutputStream out = new DerOutputStream();
        out.putInteger(val);
        byte[] encoded = out.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(encoded);
        SerialNumber sn = new SerialNumber(bais);

        // Encode again
        DerOutputStream out2 = new DerOutputStream();
        sn.encode(out2);
        byte[] encoded2 = out2.toByteArray();

        if (encoded.length != encoded2.length) {
            throw new RuntimeException("Encoded length mismatch after encode: " + encoded.length + " vs " + encoded2.length);
        }

        for (int i = 0; i < encoded.length; i++) {
            if (encoded[i] != encoded2[i]) {
                throw new RuntimeException("Encoded bytes differ at index " + i);
            }
        }
    }
}
