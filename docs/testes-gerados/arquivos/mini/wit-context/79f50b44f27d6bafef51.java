/*
 * @test
 * @summary Test ExplodedSystemModules.moduleTargets() throws InternalError as expected
 * @run main ExplodedSystemModulesWitupTest
 */

import jdk.internal.module.ExplodedSystemModules;

public class ExplodedSystemModulesWitupTest {
    public static void main(String[] args) {
        ExplodedSystemModules esm = new ExplodedSystemModules();
        try {
            esm.moduleTargets();
            throw new AssertionError("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception caught, test passes
        }
    }
}
