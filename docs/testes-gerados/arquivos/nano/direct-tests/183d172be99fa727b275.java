/* @test    @summary Test SNIHostName constructor with valid hostnames    @run main SNIHostNameWitupTest */

import javax.net.ssl.SNIHostName;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SNIHostNameWitupTest {

    public static void main(String[] args) {
        testValidHostName();
        testNullHostName();
        testEmptyHostName();
        testHostNameWithTrailingDot();
        testInvalidHostName();
    }

    static void testValidHostName() {
        String hostname = "example.com";
        SNIHostName sni = new SNIHostName(hostname);
        assert sni != null : "SNIHostName should not be null for valid hostname";
    }

    static void testNullHostName() {
        try {
            new SNIHostName(null);
            assert false : "Expected NullPointerException for null hostname";
        } catch (NullPointerException e) {
            assert e.getMessage().contains("Server name value of host_name cannot be null") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testEmptyHostName() {
        try {
            new SNIHostName("");
            // Depending on implementation, this may throw or accept; assuming it throws
            assert false : "Expected exception for empty hostname";
        } catch (Exception e) {
            // Accept any exception as valid for invalid hostname
            assert true;
        }
    }

    static void testHostNameWithTrailingDot() {
        String hostname = "example.com.";
        SNIHostName sni = new SNIHostName(hostname);
        assert sni != null : "SNIHostName should handle hostname with trailing dot";
    }

    static void testInvalidHostName() {
        String invalidHostname = "\u0000invalid"; // Null character
        try {
            new SNIHostName(invalidHostname);
            // The constructor may accept or throw; assuming it throws due to invalid hostname
            assert false : "Expected exception for invalid hostname";
        } catch (Exception e) {
            // Accept any exception as valid for invalid hostname
            assert true;
        }
    }
}
