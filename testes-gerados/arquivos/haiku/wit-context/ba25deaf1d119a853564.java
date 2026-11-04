/*
 * @test
 * @summary Smoke tests for the public ModulePatcher constructor, which is the only public entry point reachable from this method's context
 * @modules java.base/jdk.internal.module
 * @run main ModulePatcherWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        Map<String, List<String>> empty = new HashMap<>();
        ModulePatcher p1 = new ModulePatcher(empty);
        if (p1 == null) {
            throw new RuntimeException("constructor returned null for empty map");
        }

        Map<String, List<String>> input = new HashMap<>();
        List<String> paths = new ArrayList<>();
        paths.add("patches/m.a");
        input.put("m.a", paths);
        ModulePatcher p2 = new ModulePatcher(input);
        if (p2 == null) {
            throw new RuntimeException("constructor returned null for non-empty map");
        }

        Map<String, List<String>> withEmptyList = new HashMap<>();
        withEmptyList.put("m.b", Collections.<String>emptyList());
        ModulePatcher p3 = new ModulePatcher(withEmptyList);
        if (p3 == null) {
            throw new RuntimeException("constructor returned null for empty path list");
        }

        boolean npe = false;
        try {
            new ModulePatcher(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null input");
        }
    }
}
