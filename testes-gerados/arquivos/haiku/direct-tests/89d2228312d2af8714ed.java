/*
 * @test
 * @summary Basic behavior and null-argument checks of Module.isOpen(String, Module)
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        Module base = Object.class.getModule();
        Module self = ModuleWitupTest.class.getModule();

        if (!self.isOpen("java.lang", base)) {
            throw new RuntimeException("unnamed module should report open for any package");
        }
        if (!self.isOpen("no.such.pkg", base)) {
            throw new RuntimeException("unnamed module should report open for unknown package");
        }
        if (base.isOpen("java.lang", self)) {
            throw new RuntimeException("java.base must not open java.lang to the unnamed module");
        }
        if (!base.isOpen("java.lang", base)) {
            throw new RuntimeException("a named module should open its own package to itself");
        }

        checkNpe(() -> base.isOpen(null, self), "null package name");
        checkNpe(() -> base.isOpen("java.lang", null), "null target module");
    }

    private interface Call {
        void run();
    }

    private static void checkNpe(Call c, String what) {
        try {
            c.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for " + what);
    }
}
