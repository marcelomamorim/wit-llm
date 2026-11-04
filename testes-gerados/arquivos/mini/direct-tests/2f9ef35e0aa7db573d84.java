/*
 * @test
 * @summary Test setMaxPathLength method of PKIXExtendedParameters
 * @run main PKIXExtendedParametersWitupTest
 */

import java.security.InvalidAlgorithmParameterException;
import java.security.Timestamp;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.TrustAnchor;
import java.util.Collections;
import sun.security.provider.certpath.PKIXExtendedParameters;

public class PKIXExtendedParametersWitupTest {
    public static void main(String[] args) throws Exception {
        // Create minimal PKIXBuilderParameters with empty TrustAnchors
        PKIXBuilderParameters params = new PKIXBuilderParameters(Collections.<TrustAnchor>emptySet(), null);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String variant = "testVariant";

        PKIXExtendedParameters extParams = new PKIXExtendedParameters(params, ts, variant);

        // Test initial max path length
        int initialMax = extParams.getMaxPathLength();

        // Set max path length to a positive value
        extParams.setMaxPathLength(5);
        int afterSetMax = extParams.getMaxPathLength();
        if (afterSetMax != 5) {
            throw new RuntimeException("setMaxPathLength did not update max path length to 5");
        }

        // Set max path length to zero
        extParams.setMaxPathLength(0);
        if (extParams.getMaxPathLength() != 0) {
            throw new RuntimeException("setMaxPathLength did not update max path length to 0");
        }

        // Set max path length to negative value (allowed by PKIXBuilderParameters, means unlimited)
        extParams.setMaxPathLength(-1);
        if (extParams.getMaxPathLength() != -1) {
            throw new RuntimeException("setMaxPathLength did not update max path length to -1");
        }

        // Confirm that variant and timestamp remain unchanged
        if (!variant.equals(extParams.getVariant())) {
            throw new RuntimeException("Variant changed unexpectedly");
        }
        if (!ts.equals(extParams.getTimestamp())) {
            throw new RuntimeException("Timestamp changed unexpectedly");
        }

        System.out.println("All tests passed.");
    }
}
