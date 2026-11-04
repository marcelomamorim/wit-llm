/*
 * @test
 * @summary Test AuthorityInfoAccessExtension.set method behavior
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AccessDescription;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorityInfoAccessExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidDescriptions();
        testSetWithInvalidNameThrowsIOException();
        testSetWithInvalidObjectTypeThrowsIOException();
    }

    private static void testSetWithValidDescriptions() throws Exception {
        List<AccessDescription> list = new ArrayList<>();
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);
        List<AccessDescription> newList = new ArrayList<>();
        ext.set("descriptions", newList);
        // If no exception, test passes
    }

    private static void testSetWithInvalidNameThrowsIOException() {
        try {
            List<AccessDescription> list = new ArrayList<>();
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);
            ext.set("invalidName", list);
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidObjectTypeThrowsIOException() {
        try {
            List<AccessDescription> list = new ArrayList<>();
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);
            ext.set("descriptions", "not a list");
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("should be of type List")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
