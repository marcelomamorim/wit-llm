/*
 * @test
 * @summary Test sun.security.x509.X500Name emit method with DerOutputStream
 * @run main sun.security.x509.X500NameWitupTest
 */

import sun.security.x509.X500Name;
import sun.security.util.DerOutputStream;
import java.io.IOException;

public class X500NameWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a simple X500Name with commonName only
        X500Name name = new X500Name("CN=Test User");

        // Create a DerOutputStream to capture the encoding
        DerOutputStream out = new DerOutputStream();

        // Call emit and verify no exception is thrown
        name.emit(out);

        // The output should not be empty
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // Call emit again to check idempotency
        DerOutputStream out2 = new DerOutputStream();
        name.emit(out2);
        byte[] encoded2 = out2.toByteArray();
        if (encoded2 == null || encoded2.length == 0) {
            throw new RuntimeException("Second encoded output is empty");
        }

        // The two encodings should be equal
        if (encoded.length != encoded2.length) {
            throw new RuntimeException("Encoded outputs differ in length");
        }
        for (int i = 0; i < encoded.length; i++) {
            if (encoded[i] != encoded2[i]) {
                throw new RuntimeException("Encoded outputs differ at byte " + i);
            }
        }

        System.out.println("PASS");
    }
}
