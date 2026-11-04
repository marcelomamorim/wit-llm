/*
 * @test
 * @summary Tests Module.isOpen(String) for null, unopened and unknown package names
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        Module base = Object.class.getModule();
        check(!base.isOpen("java.lang"), "java.base must not open java.lang to everyone");
        check(!base.isOpen("no.such.pkg"), "unknown package must not be reported as open");

        Module self = ModuleWitupTest.class.getModule();
        if (!self.isNamed()) {
            check(self.isOpen("java.lang"), "unnamed module must report all packages as open");
            check(self.isOpen("no.such.pkg"), "unnamed module must report unknown packages as open");
        }

        boolean npe = false;
        try {
            base.isOpen(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "isOpen(null) must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
