/*
 * @test
 * @summary Verify ModulePatcher.patchIfNeeded returns the original module reference when no patches apply
 * @modules java.base/jdk.internal.module
 * @run main ModulePatcherWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        ModuleReference base = ModuleFinder.ofSystem()
                .find("java.base")
                .orElseThrow(() -> new RuntimeException("java.base not found"));

        Map<String, List<String>> input = new HashMap<>();
        input.put("some.other.module", Collections.singletonList("/nonexistent/patch"));
        ModulePatcher patcher = new ModulePatcher(input);
        ModuleReference result = patcher.patchIfNeeded(base);
        if (result != base) {
            throw new AssertionError("expected original reference when no patch matches");
        }

        ModulePatcher empty = new ModulePatcher(Collections.<String, List<String>>emptyMap());
        ModuleReference result2 = empty.patchIfNeeded(base);
        if (result2 != base) {
            throw new AssertionError("expected original reference for empty patch map");
        }
    }
}
