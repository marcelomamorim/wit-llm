/*
 * @test
 * @summary Black-box check that ModuleHashesBuilder.computeHashes returns an empty map for an empty configuration
 * @modules java.base/jdk.internal.module
 * @run main ModuleHashesBuilderWitupTest
 */

import java.lang.module.Configuration;
import java.util.Map;
import java.util.Set;
import jdk.internal.module.ModuleHashes;
import jdk.internal.module.ModuleHashesBuilder;

public class ModuleHashesBuilderWitupTest {
    public static void main(String[] args) {
        ModuleHashesBuilder builder = new ModuleHashesBuilder(Configuration.empty(), Set.of());
        Map<String, ModuleHashes> hashes = builder.computeHashes(Set.of());
        if (hashes == null) {
            throw new RuntimeException("computeHashes returned null");
        }
        if (!hashes.isEmpty()) {
            throw new RuntimeException("expected empty map but was: " + hashes);
        }
    }
}
