/*
 * @test
 * @summary Verifies that JavaLangAccess.addOpensToAllUnnamed opens the given package of the module to the unnamed module
 * @modules java.base/jdk.internal.misc
 * @run main SystemWitupTest
 */

import jdk.internal.misc.JavaLangAccess;
import jdk.internal.misc.SharedSecrets;

public class SystemWitupTest {

    public static void main(String[] args) throws Exception {
        JavaLangAccess jla = SharedSecrets.getJavaLangAccess();
        Module base = Object.class.getModule();
        Module unnamed = SystemWitupTest.class.getModule();
        String pn = "jdk.internal.misc";

        jla.addOpensToAllUnnamed(base, pn);

        if (!base.isOpen(pn, unnamed)) {
            throw new RuntimeException("Package " + pn + " should be open to the unnamed module");
        }
    }
}
