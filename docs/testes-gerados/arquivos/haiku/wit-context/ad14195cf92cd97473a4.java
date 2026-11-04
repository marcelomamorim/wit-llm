/*
 * @test
 * @summary Checks slot range validation of the JavaLangAccess.registerShutdownHook path used by System
 * @modules java.base/jdk.internal.misc
 * @run main SystemWitupTest
 */

import jdk.internal.misc.JavaLangAccess;
import jdk.internal.misc.SharedSecrets;

public class SystemWitupTest {
    public static void main(String[] args) {
        JavaLangAccess jla = SharedSecrets.getJavaLangAccess();
        expectIllegalArgument(jla, 10);
        expectIllegalArgument(jla, -1);
        jla.registerShutdownHook(5, false, () -> { });
    }

    static void expectIllegalArgument(JavaLangAccess jla, int slot) {
        try {
            jla.registerShutdownHook(slot, false, () -> { });
            throw new RuntimeException("No IllegalArgumentException for slot " + slot);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
