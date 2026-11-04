/* @test    @summary Test constructor RFC822Name(DerValue derValue) with valid and invalid DerValues    @run main RFC822NameWitupTest */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.RFC822Name;

public class RFC822NameWitupTest {

    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidDerValue();
    }

    static void testValidDerValue() throws IOException {
        String validString = "user@example.com";
        DerValue derValue = new DerValue(validString.getBytes("UTF-8"));
        derValue.setIA5String(validString);
        RFC822Name name = new RFC822Name(derValue);
        assert "user@example.com".equals(name.getName()) : "Expected name to be 'user@example.com'";
    }

    static void testInvalidDerValue() {
        try {
            DerValue invalidDerValue = new DerValue(new byte[] {0x00});
            // Simulate invalid IA5String by not setting it properly
            new RFC822Name(invalidDerValue);
            assert false : "Expected IOException for invalid DerValue";
        } catch (IOException e) {
            // Expected exception
        }
    }
}
