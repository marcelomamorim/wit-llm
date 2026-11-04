/* @test    @summary Test setMaxPathLength method of PKIXExtendedParameters class    @run main PKIXExtendedParametersWitupTest */

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.PKIXBuilderParameters;
import sun.security.provider.certpath.PKIXExtendedParameters;

public class PKIXExtendedParametersWitupTest {

    public static void main(String[] args) throws Exception {
        testSetMaxPathLength();
    }

    public static void testSetMaxPathLength() throws Exception {
        PKIXBuilderParameters params = new PKIXBuilderParameters(null, null);
        PKIXExtendedParameters extendedParams = new PKIXExtendedParameters(params, null, "variant");
        extendedParams.setMaxPathLength(5);
        int result = extendedParams.getMaxPathLength();
        if (result != 5) {
            throw new AssertionError("Expected maxPathLength to be 5, but was " + result);
        }
    }
}
