/*
 * @test
 * @summary Checks Module.isExported(String) for named and unnamed modules, and NPE on null
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Module base = Object.class.getModule();

        check(base.isNamed(), "java.base should be a named module");
        check(base.isExported("java.lang"), "java.lang should be exported unqualified by java.base");
        check(base.isExported("java.util"), "java.util should be exported unqualified by java.base");
        check(!base.isExported("no.such.pkg"), "non-existent package should not be exported");
        check(!base.isExported("sun.nio.ch"), "sun.nio.ch should not be exported unqualified");

        // Deterministic: repeated calls return the same result
        check(base.isExported("java.lang") == base.isExported("java.lang"),
              "repeated calls should be consistent");

        Module unnamed = ModuleWitupTest.class.getModule();
        check(!unnamed.isNamed(), "test class should be in an unnamed module");
        check(unnamed.isExported("java.lang"), "unnamed module should export all packages");
        check(unnamed.isExported("any.pkg"), "unnamed module should export any package name");

        boolean npeThrown = false;
        try {
            base.isExported(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "isExported(null) should throw NullPointerException");
    }
}
