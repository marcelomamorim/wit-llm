/*
 * @test
 * @summary Verify CRLReasonCodeExtension.delete accepts the reason name case-insensitively and rejects unsupported names with IOException
 * @modules java.base/sun.security.x509
 * @run main CRLReasonCodeExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {

    private static final String UNSUPPORTED_MSG =
        "Name not supported by CRLReasonCodeExtension";

    public static void main(String[] args) throws Exception {
        testDeleteReasonExact();
        testDeleteReasonCaseInsensitive();
        testDeleteReasonOnCriticalExtension();
        testDeleteUnknownNameThrows();
        testDeleteNameWithWhitespaceThrows();
        testDeleteNameConstantThrows();
    }

    private static void testDeleteReasonExact() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        ext.delete("reason");
    }

    private static void testDeleteReasonCaseInsensitive() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        ext.delete("REASON");
        ext.delete("Reason");
        ext.delete("rEaSoN");
    }

    private static void testDeleteReasonOnCriticalExtension() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(true, 2);
        ext.delete("reason");
        ext.delete("reason");
    }

    private static void testDeleteUnknownNameThrows() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        expectUnsupported(ext, "unknown");
        expectUnsupported(ext, "");
    }

    private static void testDeleteNameWithWhitespaceThrows() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        expectUnsupported(ext, "reason ");
        expectUnsupported(ext, " reason");
    }

    private static void testDeleteNameConstantThrows() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        expectUnsupported(ext, CRLReasonCodeExtension.NAME);
    }

    private static void expectUnsupported(CRLReasonCodeExtension ext, String name) {
        try {
            ext.delete(name);
        } catch (IOException e) {
            if (!UNSUPPORTED_MSG.equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for \"" + name + "\": "
                                         + e.getMessage());
            }
            return;
        }
        throw new AssertionError("Expected IOException for name \"" + name + "\"");
    }
}
