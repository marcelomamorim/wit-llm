/*
 * @test
 * @summary Verifies delete() rejects every attribute name and the int constructor rejects skipCerts below -1
 * @modules java.base/sun.security.x509
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(5);

        expectDeleteFailure(ext, "skip_certs", "may not be deleted");
        expectDeleteFailure(ext, "SKIP_CERTS", "may not be deleted");
        expectDeleteFailure(ext, "Skip_Certs", "may not be deleted");
        expectDeleteFailure(ext, "unknown", "not recognized");
        expectDeleteFailure(ext, "", "not recognized");

        expectConstructorFailure(-2);
    }

    private static void expectDeleteFailure(InhibitAnyPolicyExtension ext,
                                            String name,
                                            String expectedFragment) {
        try {
            ext.delete(name);
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains(expectedFragment)) {
                throw new RuntimeException("Unexpected message for " + name + ": " + msg);
            }
            return;
        }
        throw new RuntimeException("delete(" + name + ") did not throw IOException");
    }

    private static void expectConstructorFailure(int skipCerts) {
        try {
            new InhibitAnyPolicyExtension(skipCerts);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("constructor(" + skipCerts + ") did not throw IOException");
    }
}
