/*
 * @test
 * @summary Test sun.security.x509.X500Name.getEncoded() method for basic correctness and stability
 * @run main X500NameWitupTest
 */

import sun.security.x509.X500Name;
import java.io.IOException;

public class X500NameWitupTest {
    public static void main(String[] args) throws IOException {
        // Test 1: Basic construction and getEncoded call
        X500Name name1 = new X500Name("CN=Test User, OU=Test Unit, O=Test Org, C=US");
        byte[] encoded1 = name1.getEncoded();
        if (encoded1 == null || encoded1.length == 0) {
            throw new RuntimeException("getEncoded() returned null or empty byte array");
        }

        // Test 2: Construct with minimal DN and getEncoded
        X500Name name2 = new X500Name("CN=Minimal");
        byte[] encoded2 = name2.getEncoded();
        if (encoded2 == null || encoded2.length == 0) {
            throw new RuntimeException("getEncoded() returned null or empty byte array for minimal DN");
        }

        // Test 3: Construct with all fields constructor and getEncoded
        X500Name name3 = new X500Name("Test User", "Test Unit", "Test Org", "US");
        byte[] encoded3 = name3.getEncoded();
        if (encoded3 == null || encoded3.length == 0) {
            throw new RuntimeException("getEncoded() returned null or empty byte array for full fields constructor");
        }

        // Test 4: Check that getEncoded returns a clone (modifying returned array does not affect subsequent calls)
        byte[] encodedClone = name1.getEncoded();
        encodedClone[0] = (byte)(encodedClone[0] + 1); // modify clone
        byte[] encodedAfterModification = name1.getEncoded();
        if (encodedAfterModification[0] == encodedClone[0]) {
            throw new RuntimeException("getEncoded() does not return a clone of internal array");
        }

        // If reached here, all tests passed
        System.out.println("All X500Name.getEncoded() tests passed.");
    }
}
