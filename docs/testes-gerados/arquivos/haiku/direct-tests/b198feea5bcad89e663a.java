/*
 * @test
 * @summary Checks delete(String) of AuthorityInfoAccessExtension for the DESCRIPTIONS name and for unrecognized names
 * @modules java.base/sun.security.x509
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;

import sun.security.x509.AuthorityInfoAccessExtension;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteDescriptionsLowerCase();
        testDeleteDescriptionsUpperCase();
        testDeleteUnknownNameThrowsIOException();
        testDeleteNullNameThrowsNullPointerException();
    }

    static void testDeleteDescriptionsLowerCase() throws Exception {
        AuthorityInfoAccessExtension ext =
                new AuthorityInfoAccessExtension(new ArrayList<>());
        ext.delete("descriptions");
        ext.delete("descriptions");
    }

    static void testDeleteDescriptionsUpperCase() throws Exception {
        AuthorityInfoAccessExtension ext =
                new AuthorityInfoAccessExtension(new ArrayList<>());
        ext.delete("DESCRIPTIONS");
    }

    static void testDeleteUnknownNameThrowsIOException() throws Exception {
        AuthorityInfoAccessExtension ext =
                new AuthorityInfoAccessExtension(new ArrayList<>());
        try {
            ext.delete("bogus");
            throw new RuntimeException("Expected IOException for unknown name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("bogus")) {
                throw new RuntimeException("Message does not mention name: " + msg);
            }
        }
    }

    static void testDeleteNullNameThrowsNullPointerException() throws Exception {
        AuthorityInfoAccessExtension ext =
                new AuthorityInfoAccessExtension(new ArrayList<>());
        try {
            ext.delete(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected: name.equalsIgnoreCase is invoked on null
        }
    }
}
