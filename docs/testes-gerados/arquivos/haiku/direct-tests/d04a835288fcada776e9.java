/*
 * @test
 * @summary ModulePatcher returns module references unchanged when no patch applies
 * @modules java.base/jdk.internal.module
 * @run main ModulePatcherWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.util.Collections;
import java.util.List;
import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        ModuleReference base = ModuleFinder.ofSystem().find("java.base").get();

        ModulePatcher empty = new ModulePatcher(Collections.<String, List<String>>emptyMap());
        if (empty.patchIfNeeded(base) != base) {
            throw new RuntimeException("empty patcher must return the same reference");
        }

        ModulePatcher other = new ModulePatcher(
                Collections.singletonMap("other.mod", Collections.<String>emptyList()));
        if (other.patchIfNeeded(base) != base) {
            throw new RuntimeException("unpatched module must return the same reference");
        }
    }
}
