/*
 * @test
 * @summary Test DNSName constructor with DerValue input
 * @run main DNSNameWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.DNSName;

public class DNSNameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testDerValueThrowsIOException();
    }

    private static void testValidDerValue() throws Exception {
        // Create a DerValue with a valid IA5String
        DerValue derValue = new DerValue(DerValue.tag_IA5String, "example.com".getBytes("ASCII"));
        DNSName dnsName = new DNSName(derValue);
        // If no exception, test passed
    }

    private static void testDerValueThrowsIOException() {
        // Create a DerValue that will throw IOException on getIA5String
        DerValue derValue = new DerValue() {
            @Override
            public String getIA5String() throws IOException {
                throw new IOException("forced exception");
            }
        };
        try {
            new DNSName(derValue);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"forced exception".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
