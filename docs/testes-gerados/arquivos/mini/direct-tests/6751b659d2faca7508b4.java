/*
 * @test
 * @summary Test access and behavior of OAEPParameterSpec.DEFAULT instance
 * @run main javax.crypto.spec.OAEPParameterSpecWitupTest
 */

import javax.crypto.spec.OAEPParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.spec.PSource;

public class OAEPParameterSpecWitupTest {
    public static void main(String[] args) {
        // Access the public static final DEFAULT instance
        OAEPParameterSpec defaultSpec = OAEPParameterSpec.DEFAULT;

        // Check that defaultSpec is not null
        if (defaultSpec == null) {
            throw new RuntimeException("OAEPParameterSpec.DEFAULT is null");
        }

        // Check default values via public getters
        String mdName = defaultSpec.getDigestAlgorithm();
        if (!"SHA-1".equals(mdName)) {
            throw new RuntimeException("Expected digest algorithm SHA-1 but got " + mdName);
        }

        String mgfName = defaultSpec.getMGFAlgorithm();
        if (!"MGF1".equals(mgfName)) {
            throw new RuntimeException("Expected MGF algorithm MGF1 but got " + mgfName);
        }

        if (!(defaultSpec.getMGFParameters() instanceof MGF1ParameterSpec)) {
            throw new RuntimeException("Expected MGF parameters to be instance of MGF1ParameterSpec");
        }

        PSource pSrc = defaultSpec.getPSource();
        if (pSrc == null) {
            throw new RuntimeException("PSource in defaultSpec is null");
        }

        // Check that PSource is the default PSpecified
        if (!pSrc.equals(PSource.PSpecified.DEFAULT)) {
            throw new RuntimeException("Expected PSource.PSpecified.DEFAULT but got " + pSrc);
        }

        // Print success message
        System.out.println("OAEPParameterSpec.DEFAULT instance test passed.");
    }
}
