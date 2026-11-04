/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getT61String()
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidT61String();
        testInvalidTagT61String();
    }

    private static void testValidT61String() throws IOException {
        // Construct DER encoding for T61String with tag 0x14 (DerValue.tag_T61String)
        // Example string: "Test"
        byte[] encoded = new byte[] {
            DerValue.tag_T61String, // tag
            4,                     // length
            'T', 'e', 's', 't'     // content
        };

        DerInputStream derIn = new DerInputStream(encoded);
        String result = derIn.getT61String();

        if (!"Test".equals(result)) {
            throw new RuntimeException("Expected 'Test' but got '" + result + "'");
        }
    }

    private static void testInvalidTagT61String() {
        // Construct DER encoding with wrong tag (e.g. PrintableString 0x13) but length and content valid
        byte[] encoded = new byte[] {
            DerValue.tag_PrintableString, // wrong tag
            4,                            // length
            'T', 'e', 's', 't'            // content
        };

        try {
            DerInputStream derIn = new DerInputStream(encoded);
            derIn.getT61String();
            throw new RuntimeException("Expected IOException due to tag mismatch but none thrown");
        } catch (IOException e) {
            // Expected exception due to tag mismatch
        }
    }
}
