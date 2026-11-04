/*
 * @test
 * @summary Verifies that DisabledAlgorithmConstraints.permits(Key) rejects a key whose algorithm is listed in the disabled property and accepts an unlisted one
 * @modules java.base/sun.security.util
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import java.security.Key;
import java.security.Security;
import sun.security.util.DisabledAlgorithmConstraints;

public class DisabledAlgorithmConstraintsWitupTest {

    private static final String PROP = "jdk.test.witup.disabledAlgorithms";

    static class FakeKey implements Key {
        private final String alg;

        FakeKey(String alg) {
            this.alg = alg;
        }

        public String getAlgorithm() {
            return alg;
        }

        public String getFormat() {
            return "RAW";
        }

        public byte[] getEncoded() {
            return new byte[] {1, 2, 3, 4};
        }
    }

    public static void main(String[] args) throws Exception {
        Security.setProperty(PROP, "RSA");
        DisabledAlgorithmConstraints constraints =
                new DisabledAlgorithmConstraints(PROP);

        if (constraints.permits(new FakeKey("RSA"))) {
            throw new RuntimeException("RSA key should not be permitted");
        }
        if (!constraints.permits(new FakeKey("EC"))) {
            throw new RuntimeException("EC key should be permitted");
        }
    }
}
