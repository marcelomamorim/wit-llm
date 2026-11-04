/*
 * @test
 * @summary Checks public ModuleFinder behaviour of the boot finder that backs ModuleBootstrap.find
 * @modules java.base/jdk.internal.module
 * @run main ModuleBootstrapWitupTest
 */

import java.lang.module.ModuleFinder;
import java.util.Optional;
import java.lang.module.ModuleReference;

import jdk.internal.module.ModuleBootstrap;

public class ModuleBootstrapWitupTest {

    public static void main(String[] args) {
        ModuleFinder finder = ModuleBootstrap.unlimitedFinder();

        Optional<ModuleReference> javaBase = finder.find("java.base");
        if (!javaBase.isPresent()) {
            throw new RuntimeException("java.base not found by boot finder");
        }
        if (!"java.base".equals(javaBase.get().descriptor().name())) {
            throw new RuntimeException("unexpected module name for java.base");
        }

        Optional<ModuleReference> missing = finder.find("no.such.module.witup");
        if (missing.isPresent()) {
            throw new RuntimeException("unknown module should not be found");
        }

        Optional<ModuleReference> again = finder.find("java.base");
        if (!again.isPresent()) {
            throw new RuntimeException("repeated lookup of java.base must be stable");
        }
    }
}
