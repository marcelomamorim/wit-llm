/*
 * @test
 * @summary Test sun.security.x509.NameConstraintsExtension.get(String) method
 * @run main NameConstraintsExtensionWitupTest
 */

import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.GeneralSubtrees;
import java.io.IOException;

public class NameConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetPermittedSubtreesReturnsNull();
        testGetExcludedSubtreesReturnsNull();
        testGetThrowsIOExceptionForUnknownName();
    }

    private static void testGetPermittedSubtreesReturnsNull() throws IOException {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);
        GeneralSubtrees result = ext.get(NameConstraintsExtension.PERMITTED_SUBTREES);
        if (result != null) {
            throw new RuntimeException("Expected null for permitted subtrees when none set");
        }
    }

    private static void testGetExcludedSubtreesReturnsNull() throws IOException {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);
        GeneralSubtrees result = ext.get(NameConstraintsExtension.EXCLUDED_SUBTREES);
        if (result != null) {
            throw new RuntimeException("Expected null for excluded subtrees when none set");
        }
    }

    private static void testGetThrowsIOExceptionForUnknownName() {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);
        try {
            ext.get("unknown_name");
            throw new RuntimeException("Expected IOException for unknown attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
