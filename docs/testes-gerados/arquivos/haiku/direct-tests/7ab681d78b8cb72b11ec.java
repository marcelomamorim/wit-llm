/*
 * @test
 * @summary Verify ModuleFinder.ofSystem().find(String) behavior, which is backed by SystemModuleFinders.find, through public APIs only
 * @run main SystemModuleFindersWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.util.Optional;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws Exception {
        ModuleFinder finder = ModuleFinder.ofSystem();

        Optional<ModuleReference> base = finder.find("java.base");
        check(base != null, "find must not return null Optional");
        check(base.isPresent(), "java.base should be found in the system module finder");
        check("java.base".equals(base.get().descriptor().name()),
              "descriptor name of found module should be java.base");

        Optional<ModuleReference> missing = finder.find("no.such.module.witup");
        check(missing != null, "find must not return null Optional for unknown module");
        check(!missing.isPresent(), "unknown module name should yield empty Optional");

        Optional<ModuleReference> again = finder.find("java.base");
        check(again.isPresent(), "repeated lookup should still find java.base");

        try {
            finder.find(null);
            throw new AssertionError("find(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected: name is null
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
