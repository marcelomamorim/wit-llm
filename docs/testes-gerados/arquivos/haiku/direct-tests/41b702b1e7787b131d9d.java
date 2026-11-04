/*
 * @test
 * @summary Tests AuthorityInfoAccessExtension.set argument validation and name handling
 * @modules java.base/sun.security.x509
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sun.security.x509.AccessDescription;
import sun.security.x509.AuthorityInfoAccessExtension;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithListAcceptsValidName();
        testSetNameIsCaseInsensitive();
        testSetRejectsNonListValue();
        testSetRejectsNullValue();
        testSetRejectsUnknownName();
    }

    static void testSetWithListAcceptsValidName() throws IOException {
        AuthorityInfoAccessExtension ext =
            new AuthorityInfoAccessExtension(
                Collections.<AccessDescription>emptyList());
        List<AccessDescription> newList = new ArrayList<>();
        ext.set(AuthorityInfoAccessExtension.DESCRIPTIONS, newList);
    }

    static void testSetNameIsCaseInsensitive() throws IOException {
        AuthorityInfoAccessExtension ext =
            new AuthorityInfoAccessExtension(
                Collections.<AccessDescription>emptyList());
        ext.set("DESCRIPTIONS", new ArrayList<AccessDescription>());
        ext.set("Descriptions", new ArrayList<AccessDescription>());
    }

    static void testSetRejectsNonListValue() throws IOException {
        AuthorityInfoAccessExtension ext =
            new AuthorityInfoAccessExtension(
                Collections.<AccessDescription>emptyList());
        try {
            ext.set(AuthorityInfoAccessExtension.DESCRIPTIONS, "not a list");
            throw new RuntimeException("Expected IOException for non-List value");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testSetRejectsNullValue() throws IOException {
        AuthorityInfoAccessExtension ext =
            new AuthorityInfoAccessExtension(
                Collections.<AccessDescription>emptyList());
        try {
            ext.set(AuthorityInfoAccessExtension.DESCRIPTIONS, null);
            throw new RuntimeException("Expected IOException for null value");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testSetRejectsUnknownName() throws IOException {
        AuthorityInfoAccessExtension ext =
            new AuthorityInfoAccessExtension(
                Collections.<AccessDescription>emptyList());
        try {
            ext.set("unknownAttribute", new ArrayList<AccessDescription>());
            throw new RuntimeException("Expected IOException for unknown name");
        } catch (IOException expected) {
            // expected
        }
    }
}
