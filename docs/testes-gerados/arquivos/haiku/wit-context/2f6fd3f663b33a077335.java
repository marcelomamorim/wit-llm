/*
 * @test
 * @summary Witup-generated checks for AuthorityInfoAccessExtension.get(String)
 * @modules java.base/sun.security.x509
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sun.security.x509.AccessDescription;
import sun.security.x509.AuthorityInfoAccessExtension;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        List<AccessDescription> descriptions = new ArrayList<>();
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(descriptions);

        List<AccessDescription> result = ext.get("descriptions");
        if (!descriptions.equals(result)) {
            throw new RuntimeException("get(\"descriptions\") returned unexpected list");
        }

        result = ext.get("DESCRIPTIONS");
        if (!descriptions.equals(result)) {
            throw new RuntimeException("get(\"DESCRIPTIONS\") returned unexpected list");
        }

        checkUnrecognized(ext, "unknown");
        checkUnrecognized(ext, "");
        checkUnrecognized(ext, "descriptionsX");
    }

    private static void checkUnrecognized(AuthorityInfoAccessExtension ext, String name) {
        try {
            ext.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name [" + name + "]");
    }
}
