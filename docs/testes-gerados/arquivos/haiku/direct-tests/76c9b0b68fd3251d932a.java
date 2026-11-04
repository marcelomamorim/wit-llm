/*
 * @test
 * @summary Verify ModuleHashesBuilder.computeHashes results for candidate modules, which exercise the reverse traversal indirectly
 * @modules java.base/jdk.internal.module
 * @run main ModuleHashesBuilderWitupTest
 */

import java.lang.module.Configuration;
import java.lang.ModuleLayer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jdk.internal.module.ModuleHashes;
import jdk.internal.module.ModuleHashesBuilder;

public class ModuleHashesBuilderWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Configuration cf = ModuleLayer.boot().configuration();

        Set<String> candidates = new HashSet<>();
        candidates.add("java.base");
        ModuleHashesBuilder builder = new ModuleHashesBuilder(cf, candidates);
        Map<String, ModuleHashes> result = builder.computeHashes(candidates);
        check(result != null, "computeHashes returned null");
        check(result.containsKey("java.base"),
              "expected java.base as a leaf candidate key, got: " + result.keySet());

        ModuleHashesBuilder emptyBuilder = new ModuleHashesBuilder(cf, new HashSet<String>());
        Map<String, ModuleHashes> emptyResult = emptyBuilder.computeHashes(new HashSet<String>());
        check(emptyResult != null, "computeHashes with no candidates returned null");
        check(emptyResult.isEmpty(),
              "expected no hashes without candidates, got: " + emptyResult.keySet());
    }
}
