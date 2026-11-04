/*
 * @test
 * @summary Test java.lang.System.addOpensToAllUnnamed(Module m, String pn) for NPE and normal behavior
 * @run main SystemWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class SystemWitupTest {
    public static void main(String[] args) {
        testAddOpensToAllUnnamedWithNullModule();
        testAddOpensToAllUnnamedWithNullPackageName();
        testAddOpensToAllUnnamedNormal();
    }

    private static void testAddOpensToAllUnnamedWithNullModule() {
        System sys = new System();
        try {
            sys.addOpensToAllUnnamed(null, "some.package");
            throw new RuntimeException("Expected NullPointerException for null Module");
        } catch (NullPointerException npe) {
            // Expected
        }
    }

    private static void testAddOpensToAllUnnamedWithNullPackageName() {
        System sys = new System();
        Module m = SystemWitupTest.class.getModule();
        try {
            sys.addOpensToAllUnnamed(m, null);
            throw new RuntimeException("Expected NullPointerException for null package name");
        } catch (NullPointerException npe) {
            // Expected
        }
    }

    private static void testAddOpensToAllUnnamedNormal() {
        System sys = new System();
        Module m = SystemWitupTest.class.getModule();
        // Use a non-null package name
        String pkg = "java.lang";
        // The method does not return a value and no exception means success
        sys.addOpensToAllUnnamed(m, pkg);
    }
}
