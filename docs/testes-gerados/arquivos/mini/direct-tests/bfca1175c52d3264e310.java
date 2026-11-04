/*
 * @test
 * @summary Test ExplodedSystemModules.moduleResolutions throws InternalError
 * @run main ExplodedSystemModulesWitupTest
 */

import jdk.internal.module.ExplodedSystemModules;

public class ExplodedSystemModulesWitupTest {
    public static void main(String[] args) {
        ExplodedSystemModules esm = new ExplodedSystemModules();
        try {
            esm.moduleResolutions();
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // expected
        }
    }
}
