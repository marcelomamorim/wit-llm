/* @test    @summary Test constructor X500Name(byte[] name) for valid and invalid inputs    @run main X500NameWitupTest */

import sun.security.x509.X500Name;
import java.io.IOException;

public class X500NameWitupTest {

    public static void main(String[] args) {
        testValidDERInput();
        testInvalidDERInput();
    }

    private static void testValidDERInput() {
        byte[] validDER = new byte[] {0x30, 0x0A, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02, 0x02, 0x01, 0x03};
        try {
            X500Name name = new X500Name(validDER);
            System.out.println("Valid DER input parsed successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse valid DER input", e);
        }
    }

    private static void testInvalidDERInput() {
        byte[] invalidDER = new byte[] {0x00, 0x01, 0x02};
        boolean exceptionThrown = false;
        try {
            X500Name name = new X500Name(invalidDER);
        } catch (IOException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException for invalid DER input, but none was thrown.");
        } else {
            System.out.println("Invalid DER input correctly threw IOException.");
        }
    }
}
