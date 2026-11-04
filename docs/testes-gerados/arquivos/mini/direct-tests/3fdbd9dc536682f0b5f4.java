/*
 * @test
 * @summary Test ExplodedSystemModules.moduleReads() throws InternalError
 * @run main ExplodedSystemModulesWitupTest
 */

import java.util.Map;
import java.util.Set;

public class ExplodedSystemModulesWitupTest {
    public static void main(String[] args) {
        jdk.internal.module.ExplodedSystemModules esm = new jdk.internal.module.ExplodedSystemModules();
        try {
            esm.moduleReads();
            throw new Error("Expected InternalError not thrown");
        } catch (InternalError e) {
            // expected
        }
    }
}
