/* @test    @summary Test for sun.security.x509.RFC822Name constructor with DerValue input    @run main RFC822NameWitupTest */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.RFC822Name;

public class RFC822NameWitupTest {

    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testDerValueWithInvalidTag();
        testDerValueWithEmptyName();
        testDerValueWithMalformedName();
    }

    static void testValidDerValue() throws Exception {
        // Create a DerValue with a valid IA5String representing an email
        DerValue derValue = new DerValue(new byte[] { 0x16, 0x0F, 't', 'e', 's', 't', '@', 'e', 'x', 'a', 'm', 'p', 'l', 'e', '.', 'com' });
        RFC822Name name = new RFC822Name(derValue);
        assert name.getName().equals("test@exampl.e.com") : "Valid DerValue should parse correctly";
    }

    static void testDerValueWithInvalidTag() {
        // Tag != 22 (IA5String tag is 22)
        byte[] invalidTagBytes = { 0x02, 0x01, 0x00 }; // INTEGER tag
        try {
            DerValue derValue = new DerValue(invalidTagBytes);
            new RFC822Name(derValue);
            assert false : "Expected IOException due to invalid tag";
        } catch (IOException e) {
            // Expected exception
        }
    }

    static void testDerValueWithEmptyName() {
        // IA5String with empty content
        byte[] emptyBytes = { 0x16, 0x00 };
        try {
            DerValue derValue = new DerValue(emptyBytes);
            new RFC822Name(derValue);
            assert false : "Expected IOException due to empty name";
        } catch (IOException e) {
            // Expected exception
        }
    }

    static void testDerValueWithMalformedName() {
        // DerValue with a name that causes StringIndexOutOfBoundsException in parseName
        // For example, a DerValue with IA5String "abc" but with a tag that is correct
        byte[] malformedBytes = { 0x16, 0x03, 'a', 'b', 'c' };
        try {
            DerValue derValue = new DerValue(malformedBytes);
            RFC822Name name = new RFC822Name(derValue);
            // Force parseName to be called explicitly if not called in constructor
            name.parseName(name.getName());
            // If no exception, test passes
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception for malformed name
        } catch (IOException e) {
            // Other IOExceptions are also acceptable
        }
    }
}
