/*
 * @test
 * @summary Smoke test for the public ModulePatcher constructor with empty and non-empty patch maps
 * @modules java.base/jdk.internal.module
 * @run main ModulePatcherWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        new ModulePatcher(Collections.<String, List<String>>emptyMap());

        Map<String, List<String>> input = new HashMap<>();
        input.put("m", List.of("nonexistent-patch-dir"));
        new ModulePatcher(input);
    }
}
