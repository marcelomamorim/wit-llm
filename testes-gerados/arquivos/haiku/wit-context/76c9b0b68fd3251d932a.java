/*
 * @test
 * @summary Smoke test for ModuleHashesBuilder construction with candidate modules
 * @run main ModuleHashesBuilderWitupTest
 */

import java.util.Set;
import jdk.internal.module.ModuleHashesBuilder;

public class ModuleHashesBuilderWitupTest {
    public static void main(String[] args) {
        ModuleHashesBuilder builder = new ModuleHashesBuilder(null, Set.of("java.base"));
        if (builder == null) {
            throw new RuntimeException("constructor returned null");
        }
    }
}
