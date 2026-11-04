/*
 * @test
 * @summary Verifies ModuleLayer.findModule for boot, empty, missing and null inputs
 * @run main ModuleLayerWitupTest
 */

import java.util.Optional;

public class ModuleLayerWitupTest {

    public static void main(String[] args) {
        Optional<Module> base = ModuleLayer.boot().findModule("java.base");
        check(base.isPresent(), "boot layer must contain java.base");
        check("java.base".equals(base.get().getName()), "found module must be java.base");

        check(!ModuleLayer.boot().findModule("no.such.module.witup").isPresent(),
              "unknown module name must yield empty Optional");

        check(!ModuleLayer.empty().findModule("java.base").isPresent(),
              "empty layer must not find java.base");

        expectNpe(() -> ModuleLayer.boot().findModule(null), "boot layer null name");
        expectNpe(() -> ModuleLayer.empty().findModule(null), "empty layer null name");
    }

    interface Action {
        void run();
    }

    static void expectNpe(Action a, String what) {
        try {
            a.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException: " + what);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
