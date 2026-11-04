/*
 * @test
 * @summary Verifies that PKIXExtendedParameters.setMaxPathLength delegates to the wrapped PKIXBuilderParameters
 * @modules java.base/sun.security.provider.certpath
 * @run main PKIXExtendedParametersWitupTest
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.TrustAnchor;
import java.util.Collections;
import java.util.Set;
import sun.security.provider.certpath.PKIXExtendedParameters;

public class PKIXExtendedParametersWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        TrustAnchor ta = new TrustAnchor("CN=WitupTestCA", kp.getPublic(), null);
        Set<TrustAnchor> anchors = Collections.singleton(ta);

        PKIXBuilderParameters base = new PKIXBuilderParameters(anchors, null);
        PKIXExtendedParameters ext = new PKIXExtendedParameters(base, null, "TEST");

        ext.setMaxPathLength(3);
        check(base.getMaxPathLength() == 3, "delegate value 3 to wrapped params");
        check(ext.getMaxPathLength() == 3, "wrapper reports value 3");

        ext.setMaxPathLength(0);
        check(base.getMaxPathLength() == 0, "delegate value 0 to wrapped params");
        check(ext.getMaxPathLength() == 0, "wrapper reports value 0");

        ext.setMaxPathLength(-1);
        check(base.getMaxPathLength() == -1, "delegate value -1 to wrapped params");
        check(ext.getMaxPathLength() == -1, "wrapper reports value -1");

        ext.setMaxPathLength(5);
        check(base.getMaxPathLength() == 5, "subsequent update is delegated");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
