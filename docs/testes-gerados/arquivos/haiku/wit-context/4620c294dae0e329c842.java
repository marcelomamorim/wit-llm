/*
 * @test
 * @summary Basic deterministic behavior of DisabledAlgorithmConstraints.permits(Key) with an unset disabled-algorithms property
 * @modules java.base/sun.security.util
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import java.security.Key;
import sun.security.util.DisabledAlgorithmConstraints;

public class DisabledAlgorithmConstraintsWitupTest {

    private static final String PROPERTY_NAME = "witup.test.disabledAlgorithms.unset";

    private static final class TestKey implements Key {
        private static final long serialVersionUID = 1L;
        private final String algorithm;

        TestKey(String algorithm) {
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

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        DisabledAlgorithmConstraints constraints =
                new DisabledAlgorithmConstraints(PROPERTY_NAME);

        Key key = new TestKey("AES");
        boolean first = constraints.permits(key);
        boolean second = constraints.permits(key);
        check(first == second,
                "permits(Key) must be deterministic for the same key");

        boolean other = constraints.permits(new TestKey("AES"));
        check(first == other,
                "permits(Key) must give the same result for equal algorithm names");

        boolean rsa = constraints.permits(new TestKey("RSA"));
        boolean rsaAgain = constraints.permits(new TestKey("RSA"));
        check(rsa == rsaAgain,
                "permits(Key) must be stable across repeated calls for RSA");
    }
}
