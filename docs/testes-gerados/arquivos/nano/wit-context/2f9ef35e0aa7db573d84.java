/* @test    @summary Test for setMaxPathLength method in PKIXExtendedParameters class.    @run main PKIXExtendedParametersWitupTest */

import sun.security.provider.certpath.PKIXExtendedParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.InvalidAlgorithmParameterException;

public class PKIXExtendedParametersWitupTest {

    public static void main(String[] args) throws Exception {
        testSetMaxPathLengthNegativeOne();
        testSetMaxPathLengthZero();
        testSetMaxPathLengthPositive();
    }

    private static void testSetMaxPathLengthNegativeOne() throws Exception {
        PKIXBuilderParameters params = new PKIXBuilderParameters(null, null);
        PKIXExtendedParameters extParams = new PKIXExtendedParameters(params, null, "testVariant");
        extParams.setMaxPathLength(-1);
        if (extParams.getMaxPathLength() != -1) {
            throw new AssertionError("Expected maxPathLength to be -1");
        }
    }

    private static void testSetMaxPathLengthZero() throws Exception {
        PKIXBuilderParameters params = new PKIXBuilderParameters(null, null);
        PKIXExtendedParameters extParams = new PKIXExtendedParameters(params, null, "testVariant");
        extParams.setMaxPathLength(0);
        if (extParams.getMaxPathLength() != 0) {
            throw new AssertionError("Expected maxPathLength to be 0");
        }
    }

    private static void testSetMaxPathLengthPositive() throws Exception {
        PKIXBuilderParameters params = new PKIXBuilderParameters(null, null);
        PKIXExtendedParameters extParams = new PKIXExtendedParameters(params, null, "testVariant");
        extParams.setMaxPathLength(5);
        if (extParams.getMaxPathLength() != 5) {
            throw new AssertionError("Expected maxPathLength to be 5");
        }
    }
}
