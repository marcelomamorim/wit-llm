/*
 * @test
 * @summary Witup generated test for AuthorityInfoAccessExtension.delete(String)
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
        List<AccessDescription> list = new ArrayList<>();
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);

        ext.delete("descriptions");
        ext.delete("DESCRIPTIONS");
        ext.delete("Descriptions");

        expectIOException(ext, "unknown");
        expectIOException(ext, "");
        expectIOException(ext, "descriptionsX");

        ext.delete("descriptions");
    }

    private static void expectIOException(AuthorityInfoAccessExtension ext, String name) {
        boolean thrown = false;
        try {
            ext.delete(name);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for attribute name: [" + name + "]");
        }
    }
}
