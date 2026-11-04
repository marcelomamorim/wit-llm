/*
 * @test
 * @summary Smoke test for the public ModulePatcher constructor; the WIT expath for the anonymous PatchedModuleReader.getInputStream() is not exercised
 * @modules java.base/jdk.internal.module
 * @run main ModulePatcherWitupTest
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        Map<String, List<String>> empty = new HashMap<>();
        ModulePatcher p1 = new ModulePatcher(empty);
        if (p1 == null) {
            throw new RuntimeException("constructor returned null for empty input");
        }

        Map<String, List<String>> input = new HashMap<>();
        List<String> paths = new ArrayList<>();
        paths.add("patch-dir");
        input.put("java.base", paths);
        ModulePatcher p2 = new ModulePatcher(input);
        if (p2 == null) {
            throw new RuntimeException("constructor returned null for non-empty input");
        }
    }
}
