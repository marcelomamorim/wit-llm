/* @test    @summary Test getEncodedInternal method of X500Name for correct byte array output.    @run main X500NameWitupTest */

import java.io.IOException;
import sun.security.x509.X500Name;
import sun.security.util.DerOutputStream;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEncodedInternalReturnsByteArray();
    }

    public static void testGetEncodedInternalReturnsByteArray() throws IOException {
        // Create a minimal X500Name instance with a simple DN string
        X500Name name = new X500Name("CN=Test, O=Example");
        byte[] encodedBytes = name.getEncodedInternal();
        // Verify that the returned byte array is not null and has length > 0
        if (encodedBytes == null) {
            throw new AssertionError("getEncodedInternal returned null");
        }
        if (encodedBytes.length == 0) {
            throw new AssertionError("getEncodedInternal returned empty array");
        }
        // Optionally, verify that the byte array starts with expected DER sequence tag (0x30)
        if ((encodedBytes[0] & 0xFF) != 0x30) {
            throw new AssertionError("Encoded byte array does not start with DER sequence tag");
        }
        System.out.println("testGetEncodedInternalReturnsByteArray passed.");
    }
}
