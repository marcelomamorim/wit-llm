/*
 * @test
 * @summary Witup-generated smoke and regression checks for CRLReasonCodeExtension(int reason) constructor
 * @modules java.base/sun.security.x509
 * @run main CRLReasonCodeExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        for (int reason = 0; reason <= 10; reason++) {
            CRLReasonCodeExtension ext = new CRLReasonCodeExtension(reason);
            if (ext == null) {
                throw new RuntimeException("Constructor returned null for reason " + reason);
            }
        }

        CRLReasonCodeExtension zero = new CRLReasonCodeExtension(0);
        if (zero == null) {
            throw new RuntimeException("Constructor returned null for reason 0");
        }

        CRLReasonCodeExtension keyCompromise = new CRLReasonCodeExtension(1);
        if (keyCompromise == null) {
            throw new RuntimeException("Constructor returned null for reason 1");
        }
    }
}
