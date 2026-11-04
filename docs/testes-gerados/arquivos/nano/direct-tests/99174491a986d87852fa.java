/* @test    @summary Test for sun.security.x509.X500Name emit method    @run main X500NameWitupTest */

import sun.security.x509.X500Name;
import sun.security.util.DerOutputStream;
import java.io.IOException;

public class X500NameWitupTest {

    public static void main(String[] args) throws IOException {
        testEmitMethod();
    }

    public static void testEmitMethod() throws IOException {
        // Create a DerOutputStream to pass to emit
        DerOutputStream out = new DerOutputStream();
        // Instantiate X500Name with a simple DN string
        X500Name name = new X500Name("CN=Test, O=Example, C=US");
        // Call emit, which internally calls encode(out)
        name.emit(out);
        // Verify that the output is not empty
        byte[] encodedBytes = out.toByteArray();
        if (encodedBytes == null || encodedBytes.length == 0) {
            throw new AssertionError("emit() produced empty output");
        }
        // Additional check: output should be a valid DER encoding (starts with expected tag)
        // For simplicity, check that first byte is a DER sequence (0x30)
        if (encodedBytes[0] != 0x30) {
            throw new AssertionError("Output does not start with DER sequence tag");
        }
        System.out.println("testEmitMethod passed.");
    }
}
