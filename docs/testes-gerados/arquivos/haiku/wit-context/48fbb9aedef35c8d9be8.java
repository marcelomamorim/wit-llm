/*
 * @test
 * @summary Witup-generated checks for Checks.requirePackageName null and invalid package names
 * @modules java.base/jdk.internal.module
 * @run main ChecksWitupTest
 */

import jdk.internal.module.Checks;

public class ChecksWitupTest {

    public static void main(String[] args) {
        if (!"p.q".equals(Checks.requirePackageName("p.q"))) {
            throw new RuntimeException("valid package name not returned unchanged");
        }
        if (!"java.lang".equals(Checks.requirePackageName("java.lang"))) {
            throw new RuntimeException("valid package name not returned unchanged");
        }
        expectIAE(null);
        expectIAE("a.1b");
        expectIAE("1x");
    }

    static void expectIAE(String name) {
        try {
            Checks.requirePackageName(name);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for: " + name);
    }
}
