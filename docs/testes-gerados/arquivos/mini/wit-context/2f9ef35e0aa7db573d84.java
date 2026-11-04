/*
 * @test
 * @summary Test sun.security.provider.certpath.PKIXExtendedParameters.setMaxPathLength(int) for valid and invalid inputs
 * @run main PKIXExtendedParametersWitupTest
 */

import java.security.InvalidAlgorithmParameterException;
import java.security.Timestamp;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.TrustAnchor;
import java.security.InvalidParameterException;
import java.util.Collections;

public class PKIXExtendedParametersWitupTest {

    public static void main(String[] args) throws Exception {
        // Setup minimal PKIXBuilderParameters with empty TrustAnchors
        PKIXBuilderParameters params = new PKIXBuilderParameters(Collections.<TrustAnchor>emptySet(), null);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String variant = "testVariant";

        PKIXExtendedParameters extParams = new PKIXExtendedParameters(params, ts, variant);

        // Test valid maxPathLength values
        extParams.setMaxPathLength(0);
        if (extParams.getMaxPathLength() != 0) {
            throw new RuntimeException("Expected maxPathLength 0");
        }

        extParams.setMaxPathLength(5);
        if (extParams.getMaxPathLength() != 5) {
            throw new RuntimeException("Expected maxPathLength 5");
        }

        extParams.setMaxPathLength(-1);
        if (extParams.getMaxPathLength() != -1) {
            throw new RuntimeException("Expected maxPathLength -1");
        }

        // Test invalid maxPathLength value: less than -1 should throw InvalidParameterException
        boolean thrown = false;
        try {
            extParams.setMaxPathLength(-2);
        } catch (InvalidParameterException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected InvalidParameterException for maxPathLength < -1");
        }

        System.out.println("Test passed.");
    }
}
