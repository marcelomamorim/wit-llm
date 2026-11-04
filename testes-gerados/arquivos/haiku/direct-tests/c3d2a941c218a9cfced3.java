/*
 * @test
 * @summary Verify JavaLangAccess.definePackage delegates to ClassLoader.definePackage and returns a package with the requested name
 * @modules java.base/jdk.internal.misc
 * @run main SystemWitupTest
 */

import jdk.internal.misc.JavaLangAccess;
import jdk.internal.misc.SharedSecrets;

public class SystemWitupTest {
    public static void main(String[] args) {
        JavaLangAccess jla = SharedSecrets.getJavaLangAccess();
        ClassLoader cl = new ClassLoader() {};
        Module m = cl.getUnnamedModule();

        Package p1 = jla.definePackage(cl, "witup.pkg.one", m);
        if (p1 == null) {
            throw new RuntimeException("definePackage returned null");
        }
        if (!"witup.pkg.one".equals(p1.getName())) {
            throw new RuntimeException("Unexpected package name: " + p1.getName());
        }

        Package p2 = jla.definePackage(cl, "witup.pkg.two", m);
        if (p2 == null || !"witup.pkg.two".equals(p2.getName())) {
            throw new RuntimeException("Second package not defined correctly");
        }
    }
}
