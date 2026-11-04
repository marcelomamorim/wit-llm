/* @test    @summary Test for sun.security.pkcs.SignerInfo.encode(DerOutputStream) method
 * @run main SignerInfoWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.pkcs.SignerInfo;
import sun.security.util.DerOutputStream;

public class SignerInfoWitupTest {

    public static void main(String[] args) {
        testEncodeNormal();
        testEncodeOutOfMemoryError();
    }

    private static void testEncodeNormal() {
        try {
            // Setup minimal SignerInfo object
            byte[] dummyDigest = {0x01, 0x02};
            SignerInfo si = new SignerInfo(
                null, // issuerName, set to null for test
                null, // serial, set to null
                null, // digestAlgorithmId, null for test
                null, // digestEncryptionAlgorithmId, null for test
                dummyDigest
            );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DerOutputStream dos = new DerOutputStream(baos);
            si.encode(dos);
            byte[] encoded = baos.toByteArray();
            if (encoded.length == 0) {
                throw new AssertionError("Encoding produced empty byte array");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static void testEncodeOutOfMemoryError() {
        try {
            // Setup minimal SignerInfo object
            byte[] dummyDigest = {0x01, 0x02};
            SignerInfo si = new SignerInfo(
                null, null, null, null, dummyDigest
            );
            ByteArrayOutputStream baos = new ByteArrayOutputStream() {
                @Override
                public void write(int b) {
                    throw new OutOfMemoryError("Simulated OOM during write");
                }
            };
            DerOutputStream dos = new DerOutputStream(baos);
            si.encode(dos); // Should propagate OutOfMemoryError
            throw new AssertionError("Expected OutOfMemoryError was not thrown");
        } catch (OutOfMemoryError e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}
