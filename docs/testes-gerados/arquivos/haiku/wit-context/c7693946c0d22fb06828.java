/*
 * @test
 * @summary Checks public permits(Set, Key) behavior of DisabledAlgorithmConstraints against the certpath disabled algorithms property
 * @modules java.base/sun.security.util
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import java.security.CryptoPrimitive;
import java.security.Key;
import java.security.Security;
import java.util.EnumSet;
import java.util.Set;

import sun.security.util.DisabledAlgorithmConstraints;

public class DisabledAlgorithmConstraintsWitupTest {

    static final class TestKey implements Key {
        private static final long serialVersionUID = 1L;
        private final String alg;

        TestKey(String alg) {
            this.alg = alg;
        }

        public String getAlgorithm() {
            return alg;
        }

        public String getFormat() {
            return null;
        }

        public byte[] getEncoded() {
            return new byte[0];
        }
    }

    public static void main(String[] args) throws Exception {
        String prop = DisabledAlgorithmConstraints.PROPERTY_CERTPATH_DISABLED_ALGS;
        String original = Security.getProperty(prop);
        Set<CryptoPrimitive> sig = EnumSet.of(CryptoPrimitive.SIGNATURE);
        try {
            Security.setProperty(prop, "MD5");
            DisabledAlgorithmConstraints disabled = new DisabledAlgorithmConstraints(prop);
            if (disabled.permits(sig, new TestKey("MD5"))) {
                throw new RuntimeException("Disabled key algorithm MD5 was permitted");
            }

            Security.setProperty(prop, "");
            DisabledAlgorithmConstraints empty = new DisabledAlgorithmConstraints(prop);
            if (!empty.permits(sig, new TestKey("MD5"))) {
                throw new RuntimeException("Key was rejected with empty disabled algorithms list");
            }
        } finally {
            Security.setProperty(prop, original == null ? "" : original);
        }
    }
}
