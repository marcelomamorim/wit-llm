/* @test    @summary Test for RDN constructor with AVA parameter, including null input handling    @run main RDNWitupTest */

import sun.security.x509.RDN;
import sun.security.x509.AVA;
import java.io.IOException;

public class RDNWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithValidAva();
        testConstructorWithNullAva();
    }

    private static void testConstructorWithValidAva() {
        try {
            AVA ava = new AVA(); // Assuming default constructor exists; replace with actual if needed
            RDN rdn = new RDN(ava);
            if (rdn == null) {
                throw new AssertionError("RDN instance should not be null for valid AVA");
            }
            System.out.println("testConstructorWithValidAva passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testConstructorWithValidAva: " + e);
        }
    }

    private static void testConstructorWithNullAva() {
        try {
            RDN rdn = new RDN((AVA) null);
            throw new AssertionError("Expected NullPointerException for null AVA, but none was thrown.");
        } catch (NullPointerException e) {
            System.out.println("testConstructorWithNullAva passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type in testConstructorWithNullAva: " + e);
        }
    }
}
