/*
 * @test
 * @summary Verifies that the JavaLangAccess.addReadsAllUnnamed implementation in System makes a named module read the unnamed module, and rejects a null module.
 * @modules java.base/jdk.internal.misc
 * @run main SystemWitupTest
 */

import jdk.internal.misc.JavaLangAccess;
import jdk.internal.misc.SharedSecrets;

public class SystemWitupTest {
    public static void main(String[] args) {
        JavaLangAccess jla = SharedSecrets.getJavaLangAccess();
        Module base = Object.class.getModule();
        Module unnamed = SystemWitupTest.class.getModule();

        if (!unnamed.isNamed() && base.canRead(unnamed)) {
            throw new RuntimeException("precondition: test module already readable");
        }

        jla.addReadsAllUnnamed(base);

        if (!base.canRead(unnamed)) {
            throw new RuntimeException("java.base should read the unnamed module after addReadsAllUnnamed");
        }

        boolean npeThrown = false;
        try {
            jla.addReadsAllUnnamed(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("null module should cause NullPointerException");
        }
    }
}
