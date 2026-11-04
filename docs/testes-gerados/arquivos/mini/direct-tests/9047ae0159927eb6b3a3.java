/*
 * @test
 * @summary Test sun.security.x509.X500Name.getEncoded() returns a clone of the internal encoding
 * @run main X500NameWitupTest
 */

import sun.security.x509.X500Name;
import java.io.IOException;
import java.util.Arrays;

public class X500NameWitupTest {
    public static void main(String[] args) throws Exception {
        // Create X500Name from a distinguished name string
        X500Name name = new X500Name("CN=Test, OU=Dev, O=OpenJDK, C=US");

        // Call getEncoded() twice and verify that the returned arrays are equal but distinct objects
        byte[] encoded1 = name.getEncoded();
        byte[] encoded2 = name.getEncoded();

        if (encoded1 == encoded2) {
            throw new RuntimeException("getEncoded() returned the same array instance on multiple calls");
        }

        if (!Arrays.equals(encoded1, encoded2)) {
            throw new RuntimeException("getEncoded() returned different content on multiple calls");
        }

        // Modify the returned array and verify it does not affect subsequent calls
        encoded1[0] = (byte)(encoded1[0] + 1);
        byte[] encoded3 = name.getEncoded();

        if (encoded1[0] == encoded3[0]) {
            throw new RuntimeException("Modifying returned array affected internal state");
        }

        System.out.println("Test passed.");
    }
}
