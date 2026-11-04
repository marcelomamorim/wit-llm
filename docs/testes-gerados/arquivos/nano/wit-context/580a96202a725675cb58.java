/* @test    @summary Test for X500Name(byte[] name) constructor handling valid and invalid inputs    @run main X500NameWitupTest */

import sun.security.x509.X500Name;
import java.io.IOException;

public class X500NameWitupTest {

    public static void main(String[] args) {
        testValidInput();
        testInputWithShortLength();
    }

    private static void testValidInput() {
        byte[] validName = {0x30, 0x82, 0x01, 0x0A}; // Example DER-encoded data
        try {
            X500Name name = new X500Name(validName);
            System.out.println("Valid input test passed.");
        } catch (IOException e) {
            System.err.println("Valid input test failed: " + e);
        }
    }

    private static void testInputWithShortLength() {
        byte[] shortName = {0x30, 0x00}; // Length less than 2, should trigger IOException
        try {
            X500Name name = new X500Name(shortName);
            System.err.println("Short length input test failed: exception expected but not thrown.");
        } catch (IOException e) {
            System.out.println("Short length input test passed: " + e.getMessage());
        }
    }
}
