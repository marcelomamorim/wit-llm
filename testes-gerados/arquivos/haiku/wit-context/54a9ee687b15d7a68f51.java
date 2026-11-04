/*
 * @test
 * @summary WITUP-generated checks for CRLReasonCodeExtension.delete(String)
 * @modules java.base/sun.security.x509
 * @run main CRLReasonCodeExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteReasonNameAccepted();
        testDeleteReasonNameCaseInsensitive();
        testDeleteWithZeroReason();
        testDeleteTwiceIsAccepted();
        testDeleteUnsupportedNamesThrow();
    }

    static void testDeleteReasonNameAccepted() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        ext.delete("reason");
    }

    static void testDeleteReasonNameCaseInsensitive() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        ext.delete("REASON");
        ext = new CRLReasonCodeExtension(2);
        ext.delete("Reason");
    }

    static void testDeleteWithZeroReason() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(0);
        ext.delete("reason");
    }

    static void testDeleteTwiceIsAccepted() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(true, 3);
        ext.delete("reason");
        ext.delete("reason");
    }

    static void testDeleteUnsupportedNamesThrow() throws IOException {
        String[] names = {"foo", "", "reasons", "reason ", "critical"};
        for (String name : names) {
            CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
            boolean thrown = false;
            try {
                ext.delete(name);
            } catch (IOException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IOException for name: " + name);
            }
        }
    }
}
