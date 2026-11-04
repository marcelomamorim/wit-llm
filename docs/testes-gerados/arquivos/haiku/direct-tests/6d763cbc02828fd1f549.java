/*
 * @test
 * @summary Behavioral checks for Module.addOpens(String, Module) on unnamed and named modules
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Module unnamed = ModuleWitupTest.class.getClassLoader().getUnnamedModule();
        check(!unnamed.isNamed(), "unnamed module must not be named");

        Module r = unnamed.addOpens("any.pkg", unnamed);
        check(r == unnamed, "addOpens on unnamed module must return this");

        try {
            unnamed.addOpens(null, unnamed);
            check(false, "null package must throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }

        try {
            unnamed.addOpens("any.pkg", null);
            check(false, "null other module must throw NullPointerException");
        } catch (NullPointerException expected) {
        }

        Module base = Object.class.getModule();
        check(base.isNamed(), "java.base must be named");
        try {
            base.addOpens("java.lang", unnamed);
            check(false, "addOpens on non-open package of named module must throw IllegalCallerException");
        } catch (IllegalCallerException expected) {
        }

        try {
            base.addOpens(null, unnamed);
            check(false, "null package on named module must throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
