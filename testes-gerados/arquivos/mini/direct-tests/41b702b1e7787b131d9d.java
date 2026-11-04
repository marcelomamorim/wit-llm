/*
 * @test
 * @summary Test AuthorityInfoAccessExtension.set method behavior
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AccessDescription;
import sun.security.x509.PKIXExtensions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidDescriptions();
        testSetWithInvalidType();
        testSetWithUnrecognizedName();
    }

    private static void testSetWithValidDescriptions() throws Exception {
        List<AccessDescription> list = new ArrayList<>();
        // Create AuthorityInfoAccessExtension with empty list
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);

        // Create new list with one AccessDescription
        List<AccessDescription> newList = new ArrayList<>();
        AccessDescription ad = new AccessDescription(PKIXExtensions.AuthInfoAccess_Id, null);
        newList.add(ad);

        ext.set("descriptions", newList);

        // After set, no exception and internal list should be updated
        // We cannot access private field, but encodeThis() is called without exception
        // So test passes if no exception thrown
    }

    private static void testSetWithInvalidType() {
        try {
            List<AccessDescription> list = new ArrayList<>();
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);

            ext.set("descriptions", "not a list");
            throw new RuntimeException("Expected IOException not thrown for invalid type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"Attribute value should be of type List.".equals(msg)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testSetWithUnrecognizedName() {
        try {
            List<AccessDescription> list = new ArrayList<>();
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);

            ext.set("unknownName", list);
            throw new RuntimeException("Expected IOException not thrown for unrecognized name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.startsWith("Attribute name [unknownName] not recognized by CertAttrSet:AuthorityInfoAccessExtension.")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}
