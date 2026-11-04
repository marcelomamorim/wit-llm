/*
 * @test
 * @summary Verify that System's JavaLangAccess.addOpensToAllUnnamed rejects null arguments with NullPointerException
 * @modules java.base/jdk.internal.misc
 * @run main SystemWitupTest
 */

import jdk.internal.misc.JavaLangAccess;
import jdk.internal.misc.SharedSecrets;

public class SystemWitupTest {

    public static void main(String[] args) {
        JavaLangAccess jla = SharedSecrets.getJavaLangAccess();
        Module base = Object.class.getModule();

        expectNpe(() -> jla.addOpensToAllUnnamed(base, null), "null package name");
        expectNpe(() -> jla.addOpensToAllUnnamed(null, "java.lang"), "null module");
    }

    private static void expectNpe(Runnable r, String label) {
        try {
            r.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for " + label);
    }
}
