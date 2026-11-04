/*
 * @test
 * @summary Checks that ModulePatcher returns the original module reference when no patches apply to the module
 * @modules java.base/jdk.internal.module
 * @run main ModulePatcherWitupTest
 */

import java.lang.module.ModuleReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) throws Exception {
        ModuleReference ref = ModuleLayer.boot()
                .configuration()
                .findModule("java.base")
                .get()
                .reference();

        ModulePatcher emptyPatcher = new ModulePatcher(Collections.emptyMap());
        ModuleReference r1 = emptyPatcher.patchIfNeeded(ref);
        if (r1 != ref) {
            throw new RuntimeException("empty patcher must return the same reference");
        }

        Map<String, List<String>> input = new HashMap<>();
        input.put("java.naming", Collections.emptyList());
        ModulePatcher otherPatcher = new ModulePatcher(input);
        ModuleReference r2 = otherPatcher.patchIfNeeded(ref);
        if (r2 != ref) {
            throw new RuntimeException("patcher without patches for java.base must return the same reference");
        }
    }
}
