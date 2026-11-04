/*
 * @test
 * @summary Verify PKIXExtendedParameters.setMaxPathLength delegates to the wrapped parameters and rejects values below -1
 * @modules java.base/sun.security.provider.certpath
 * @run main PKIXExtendedParametersWitupTest
 */

import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.TrustAnchor;
import java.util.Collections;
import sun.security.provider.certpath.PKIXExtendedParameters;

public class PKIXExtendedParametersWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        TrustAnchor anchor = new TrustAnchor("CN=WitupTest", kp.getPublic(), null);
        PKIXBuilderParameters base =
                new PKIXBuilderParameters(Collections.singleton(anchor), null);
        PKIXExtendedParameters ext =
                new PKIXExtendedParameters(base, null, "generic");

        ext.setMaxPathLength(3);
        check(ext.getMaxPathLength() == 3, "wrapper should report 3");
        check(base.getMaxPathLength() == 3, "wrapped params should report 3");

        ext.setMaxPathLength(-1);
        check(ext.getMaxPathLength() == -1, "-1 should be accepted");

        ext.setMaxPathLength(0);
        check(ext.getMaxPathLength() == 0, "0 should be accepted");

        boolean thrown = false;
        try {
            ext.setMaxPathLength(-2);
        } catch (InvalidParameterException e) {
            thrown = true;
        }
        check(thrown, "values below -1 must throw InvalidParameterException");
        check(ext.getMaxPathLength() == 0,
                "rejected value must not change the stored path length");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
