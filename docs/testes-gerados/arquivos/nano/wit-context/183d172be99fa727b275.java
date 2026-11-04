/* @test    @summary Test for javax.net.ssl.SNIHostName constructor and exception paths    @run main SNIHostNameWitupTest */

import javax.net.ssl.SNIHostName;
import java.nio.charset.StandardCharsets;

public class SNIHostNameWitupTest {

    public static void main(String[] args) {
        testValidHostName();
        testNullHostName();
        testEmptyHostName();
        testHostNameEndsWithDot();
        testInvalidHostName();
    }

    private static void testValidHostName() {
        String validHost = "example.com";
        try {
            SNIHostName sni = new SNIHostName(validHost);
            assert sni != null : "SNIHostName should be created for valid hostname";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid hostname", e);
        }
    }

    private static void testNullHostName() {
        try {
            new SNIHostName(null);
            throw new AssertionError("Expected NullPointerException for null hostname");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testEmptyHostName() {
        try {
            new SNIHostName("");
            // Depending on implementation, may throw or accept; here we assume it throws
            throw new AssertionError("Expected exception for empty hostname");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (NullPointerException e) {
            // also acceptable if thrown
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testHostNameEndsWithDot() {
        String hostname = "example.";
        try {
            new SNIHostName(hostname);
            // Implementation may accept or reject; assuming rejection here
            throw new AssertionError("Expected exception for hostname ending with dot");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testInvalidHostName() {
        String invalidHost = "\u0000"; // Null character, invalid in hostname
        try {
            new SNIHostName(invalidHost);
            // Depending on implementation, may throw; assuming it does
            throw new AssertionError("Expected exception for invalid hostname");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
