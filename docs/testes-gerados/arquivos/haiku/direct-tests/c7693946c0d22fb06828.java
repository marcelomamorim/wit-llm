/*
 * @test
 * @summary Checks DisabledAlgorithmConstraints.permits(Set, Key) against a disabled algorithm name
 * @modules java.base/sun.security.util
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import java.security.CryptoPrimitive;
import java.security.Key;
import java.security.Security;
import java.util.Collections;
import java.util.Set;

import sun.security.util.DisabledAlgorithmConstraints;

public class DisabledAlgorithmConstraintsWitupTest {

    static final String DISABLED_PROP = "jdk.test.witup.disabledAlgs";
    static final String EMPTY_PROP = "jdk.test.witup.emptyAlgs";

    static final class FakeKey implements Key {
        private static final long serialVersionUID = 1L;
        private final String alg;

        FakeKey(String alg) {
            this.alg = alg;
        }

        @Override
        public String getAlgorithm() {
            return alg;
        }

        @Override
        public String getFormat() {
            return "RAW";
        }

        @Override
        public byte[] getEncoded() {
            return new byte[] {1, 2, 3};
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        Security.setProperty(DISABLED_PROP, "DSA");
        Security.setProperty(EMPTY_PROP, "");

        DisabledAlgorithmConstraints constraints =
                new DisabledAlgorithmConstraints(DISABLED_PROP);
        Set<CryptoPrimitive> sig =
                Collections.singleton(CryptoPrimitive.SIGNATURE);

        // Key whose algorithm is explicitly disabled must not be permitted
        check(!constraints.permits(sig, new FakeKey("DSA")),
                "disabled DSA key should not be permitted");

        // Key whose algorithm is not listed must be permitted
        check(constraints.permits(sig, new FakeKey("RSA")),
                "non-disabled RSA key should be permitted");

        // With an empty property, nothing is disabled
        DisabledAlgorithmConstraints empty =
                new DisabledAlgorithmConstraints(EMPTY_PROP);
        check(empty.permits(sig, new FakeKey("DSA")),
                "DSA key should be permitted when nothing is disabled");
    }
}
