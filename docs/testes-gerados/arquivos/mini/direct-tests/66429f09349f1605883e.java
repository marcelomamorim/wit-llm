/*
 * @test
 * @summary Test RFC822Name constructor with DerValue input
 * @run main RFC822NameWitupTest
 */

import java.io.IOException;
import sun.security.x509.RFC822Name;
sun.security.util.DerValue;

public class RFC822NameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidDerValueEmptyString();
        testInvalidDerValueNoDomain();
        testInvalidDerValueDomainDotOnly();
    }

    private static void testValidDerValue() throws Exception {
        // DerValue with valid IA5String "user@example.com"
        DerValue derValue = new DerValue((byte)22, "user@example.com".getBytes("ASCII"));
        RFC822Name rfc822Name = new RFC822Name(derValue);
        if (!"user@example.com".equals(rfc822Name.getName())) {
            throw new RuntimeException("Expected name 'user@example.com' but got '" + rfc822Name.getName() + "'");
        }
        if (rfc822Name.getType() != 1) { // GeneralNameInterface.NAME_RFC822 == 1
            throw new RuntimeException("Expected type 1 but got " + rfc822Name.getType());
        }
    }

    private static void testInvalidDerValueEmptyString() {
        try {
            DerValue derValue = new DerValue((byte)22, "".getBytes());
            new RFC822Name(derValue);
            throw new RuntimeException("Expected IOException for empty string");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("may not be null or empty")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testInvalidDerValueNoDomain() {
        try {
            DerValue derValue = new DerValue((byte)22, "user@".getBytes("ASCII"));
            new RFC822Name(derValue);
            throw new RuntimeException("Expected IOException for domain empty after '@'");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("may not end with @")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testInvalidDerValueDomainDotOnly() {
        try {
            DerValue derValue = new DerValue((byte)22, "user@.".getBytes("ASCII"));
            new RFC822Name(derValue);
            throw new RuntimeException("Expected IOException for domain '.' only");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("domain may not be just .")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
