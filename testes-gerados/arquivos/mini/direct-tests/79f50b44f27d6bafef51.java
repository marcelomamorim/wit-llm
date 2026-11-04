/*
 * @test
 * @summary Test ExplodedSystemModules.moduleTargets() throws InternalError
 * @run main ExplodedSystemModulesWitupTest
 */

import jdk.internal.module.ExplodedSystemModules;

public class ExplodedSystemModulesWitupTest {
    public static void main(String[] args) {
        ExplodedSystemModules esm = new ExplodedSystemModules();
        try {
            esm.moduleTargets();
            throw new Error("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }
}
