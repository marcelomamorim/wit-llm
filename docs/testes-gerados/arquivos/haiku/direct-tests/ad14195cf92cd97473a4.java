/*
 * @test
 * @summary Registers a system shutdown hook through JavaLangAccess, which delegates to the registerShutdownHook implementation in System.
 * @modules java.base/jdk.internal.misc
 * @run main SystemWitupTest
 */

import jdk.internal.misc.JavaLangAccess;
import jdk.internal.misc.SharedSecrets;

public class SystemWitupTest {
    public static void main(String[] args) {
        JavaLangAccess jla = SharedSecrets.getJavaLangAccess();
        if (jla == null) {
            throw new RuntimeException("JavaLangAccess is null");
        }
        jla.registerShutdownHook(7, false, () -> { });
    }
}
