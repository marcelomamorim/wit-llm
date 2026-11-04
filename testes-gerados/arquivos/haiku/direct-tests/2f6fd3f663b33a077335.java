/*
 * @test
 * @summary Verify AuthorityInfoAccessExtension.get returns the descriptions list for a recognized name and throws IOException for unknown names
 * @modules java.base/sun.security.x509
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sun.security.x509.AuthorityInfoAccessExtension;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        AuthorityInfoAccessExtension ext =
                new AuthorityInfoAccessExtension(new ArrayList<>());

        Object first = ext.get("descriptions");
        Object second = ext.get("DESCRIPTIONS");
        if (first == null) {
            throw new AssertionError("get(\"descriptions\") returned null");
        }
        if (first != second) {
            throw new AssertionError("case-insensitive lookup returned a different list");
        }
        if (((List<?>) first).size() != 0) {
            throw new AssertionError("expected empty descriptions list");
        }

        try {
            ext.get("unknownAttribute");
            throw new AssertionError("expected IOException for unknown attribute");
        } catch (IOException expected) {
            // expected
        }
    }
}
