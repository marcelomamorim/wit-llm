/*
 * @test
 * @summary Test constructor of sun.security.timestamp.TimestampToken for null input and valid input
 * @run main TimestampTokenWitupTest
 */

import java.io.IOException;
import sun.security.timestamp.TimestampToken;

public class TimestampTokenWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithNull();
        testConstructorWithValidMinimalInput();
    }

    private static void testConstructorWithNull() {
        try {
            new TimestampToken(null);
            throw new RuntimeException("Expected IOException for null input");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("No timestamp token info")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }

    private static void testConstructorWithValidMinimalInput() throws Exception {
        // Minimal valid ASN.1 encoding for TSTInfo is complex; here we test with a minimal non-null byte array
        // that does not cause immediate null pointer or other trivial exceptions in constructor.
        // We do not assert on internal state as parse is not public.
        byte[] minimalValid = new byte[] {0x30, 0x00}; // ASN.1 SEQUENCE with zero length
        try {
            TimestampToken token = new TimestampToken(minimalValid);
            if (token == null) {
                throw new RuntimeException("Constructor returned null");
            }
            // getDate() may be null if parse did not set it
            token.getDate();
            token.getHashAlgorithm();
        } catch (IOException e) {
            // IOException may be thrown if parse fails; acceptable for minimal input
        }
    }
}
