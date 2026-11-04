/*
 * @test
 * @summary Observable constructor behavior of ModulePatcher, the only public entry point reachable for this unit
 * @modules java.base/jdk.internal.module
 * @run main ModulePatcherWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        testEmptyMapConstructs();
        testNonEmptyMapConstructs();
        testNullInputThrowsNPE();
    }

    static void testEmptyMapConstructs() {
        Map<String, List<String>> input = new HashMap<>();
        ModulePatcher patcher = new ModulePatcher(input);
        if (patcher == null) {
            throw new RuntimeException("constructor returned null");
        }
    }

    static void testNonEmptyMapConstructs() {
        Map<String, List<String>> input = new HashMap<>();
        input.put("java.base", Arrays.asList("patch-dir"));
        input.put("java.sql", Collections.<String>emptyList());
        ModulePatcher patcher = new ModulePatcher(input);
        if (patcher == null) {
            throw new RuntimeException("constructor returned null");
        }
    }

    static void testNullInputThrowsNPE() {
        try {
            new ModulePatcher(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null input");
    }
}
