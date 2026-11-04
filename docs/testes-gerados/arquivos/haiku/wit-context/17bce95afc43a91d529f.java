/*
 * @test
 * @summary Witup-generated smoke test for ModuleHashesBuilder.computeHashes on an empty configuration
 * @modules java.base/jdk.internal.module
 * @run main ModuleHashesBuilderWitupTest
 */

import java.lang.module.Configuration;
import java.util.Map;
import java.util.Set;
import jdk.internal.module.ModuleHashesBuilder;

public class ModuleHashesBuilderWitupTest {
    public static void main(String[] args) {
        Configuration cf = Configuration.empty();
        ModuleHashesBuilder builder = new ModuleHashesBuilder(cf, Set.of());
        Map<?, ?> hashes = builder.computeHashes(Set.of());
        if (hashes == null) {
            throw new RuntimeException("computeHashes returned null");
        }
        if (!hashes.isEmpty()) {
            throw new RuntimeException("expected empty map but was " + hashes);
        }
    }
}
