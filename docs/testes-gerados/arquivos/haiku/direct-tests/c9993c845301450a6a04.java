/*
 * @test
 * @summary Verify denyAfter handling of DisabledAlgorithmConstraints through its public permits(Set, Key) API
 * @modules java.base/sun.security.util
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import java.security.Key;
import java.security.Security;
import java.security.CryptoPrimitive;
import java.util.EnumSet;
import java.util.Set;

import sun.security.util.DisabledAlgorithmConstraints;

public class DisabledAlgorithmConstraintsWitupTest {

    private static final String PROP = "jdk.test.witup.disabledAlgs";

    static Key keyFor(final String alg) {
        return new Key() {
            public String getAlgorithm() { return alg; }
            public String getFormat() { return "RAW"; }
            public byte[] getEncoded() { return new byte[] { 1, 2, 3, 4 }; }
        };
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        Set<CryptoPrimitive> prims = EnumSet.of(CryptoPrimitive.SIGNATURE);

        Security.setProperty(PROP, "XYZ denyAfter 2000-01-01");
        DisabledAlgorithmConstraints expired =
                new DisabledAlgorithmConstraints(PROP);
        check(!expired.permits(prims, keyFor("XYZ")),
              "key with expired denyAfter date must not be permitted");

        Security.setProperty(PROP, "XYZ denyAfter 2999-12-31");
        DisabledAlgorithmConstraints future =
                new DisabledAlgorithmConstraints(PROP);
        check(future.permits(prims, keyFor("XYZ")),
              "key with future denyAfter date must still be permitted");

        Security.setProperty(PROP, "");
        DisabledAlgorithmConstraints none =
                new DisabledAlgorithmConstraints(PROP);
        check(none.permits(prims, keyFor("XYZ")),
              "key must be permitted when no constraint is configured");
    }
}
