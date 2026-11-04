/* @test    @summary Test DNSName constructor with DerValue input    @run main DNSNameWitupTest */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.DNSName;

public class DNSNameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testNullName();
        testEmptyDerValue();
    }

    static void testValidDerValue() throws IOException {
        String validName = "example.com";
        DerValue derValue = new DerValue(validName.getBytes("ISO-8859-1"));
        derValue.setIA5String(validName);
        DNSName dnsName = new DNSName(derValue);
        assert dnsName != null : "DNSName should be created successfully";
    }

    static void testNullName() {
        try {
            DerValue derValue = new DerValue(new byte[] {0x16, 0x0A, 'e', 'x', 'a', 'm', 'p', 'l', 'e', '.', 'c', 'o', 'm'});
            derValue.setIA5String(null);
            new DNSName(derValue);
            assert false : "Expected IOException for null name";
        } catch (IOException e) {
            assert e.getMessage().equals("DNS name must not be null") : "Unexpected exception message";
        }
    }

    static void testEmptyDerValue() {
        try {
            DerValue derValue = new DerValue(new byte[] {0x16, 0x00});
            derValue.setIA5String("");
            new DNSName(derValue);
            assert false : "Expected IOException for empty name";
        } catch (IOException e) {
            // Expected exception
        }
    }
}
