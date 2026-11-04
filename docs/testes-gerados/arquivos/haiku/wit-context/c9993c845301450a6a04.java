/*
 * @test
 * @summary Checks that DisabledAlgorithmConstraints.permits(Key) allows keys when the configured property does not disable anything
 * @modules java.base/sun.security.util
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import java.security.Key;
import sun.security.util.DisabledAlgorithmConstraints;

public class DisabledAlgorithmConstraintsWitupTest {

    private static final String UNSET_PROPERTY = "witup.test.unset.disabledAlgorithms";

    static class StubKey implements Key {
        private static final long serialVersionUID = 1L;
        private final String algorithm;

        StubKey(String algorithm) {
            this.algorithm = algorithm;
        }

        @Override
        public String getAlgorithm() {
            return algorithm;
        }

        @Override
        public String getFormat() {
            return "RAW";
        }

        @Override
        public byte[] getEncoded() {
            return new byte[] { 1, 2, 3, 4 };
        }
    }

    public static void main(String[] args) throws Exception {
        DisabledAlgorithmConstraints constraints =
                new DisabledAlgorithmConstraints(UNSET_PROPERTY);

        Key rsaKey = new StubKey("RSA");
        if (!constraints.permits(rsaKey)) {
            throw new RuntimeException("RSA key should be permitted when nothing is disabled");
        }

        Key ecKey = new StubKey("EC");
        if (!constraints.permits(ecKey)) {
            throw new RuntimeException("EC key should be permitted when nothing is disabled");
        }

        if (!constraints.permits(rsaKey)) {
            throw new RuntimeException("permits(Key) should be repeatable for the same key");
        }
    }
}
