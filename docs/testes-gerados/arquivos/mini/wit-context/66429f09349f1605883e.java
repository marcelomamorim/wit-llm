/*
 * @test
 * @summary Test sun.security.x509.RFC822Name constructor with DerValue input for valid and invalid cases
 * @run main RFC822NameWitupTest
 */

import sun.security.x509.RFC822Name;
import sun.security.util.DerValue;
import java.io.IOException;

public class RFC822NameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testDerValueWithNullIA5String();
        testDerValueWithEmptyIA5String();
        testDerValueWithInvalidDomain();
    }

    private static void testValidDerValue() throws Exception {
        // DerValue with tag 22 (IA5String) and valid email
        DerValue der = new DerValue(DerValue.tag_IA5String, "user@example.com");
        RFC822Name rfc822Name = new RFC822Name(der);
        if (!"user@example.com".equals(rfc822Name.getName())) {
            throw new RuntimeException("Valid DerValue test failed: name mismatch");
        }
        if (rfc822Name.getType() != 1) { // GeneralNameInterface.NAME_RFC822 == 1
            throw new RuntimeException("Valid DerValue test failed: type mismatch");
        }
    }

    private static void testDerValueWithNullIA5String() {
        // DerValue.getIA5String() returns null simulating invalid tag
        DerValue der = new DerValue() {
            @Override
            public String getIA5String() {
                return null;
            }
        };
        try {
            new RFC822Name(der);
            throw new RuntimeException("Expected IOException for null IA5String not thrown");
        } catch (IOException e) {
            // Expected IOException due to parseName(null)
        }
    }

    private static void testDerValueWithEmptyIA5String() {
        // DerValue.getIA5String() returns empty string simulating invalid input
        DerValue der = new DerValue() {
            @Override
            public String getIA5String() {
                return "";
            }
        };
        try {
            new RFC822Name(der);
            throw new RuntimeException("Expected IOException for empty IA5String not thrown");
        } catch (IOException e) {
            // Expected IOException due to parseName("")
        }
    }

    private static void testDerValueWithInvalidDomain() {
        // DerValue.getIA5String() returns string ending with '@' which is invalid
        DerValue der = new DerValue() {
            @Override
            public String getIA5String() {
                return "user@";
            }
        };
        try {
            new RFC822Name(der);
            throw new RuntimeException("Expected IOException for domain ending with @ not thrown");
        } catch (IOException e) {
            // Expected IOException due to domain length 0 after '@'
        }
    }
}
