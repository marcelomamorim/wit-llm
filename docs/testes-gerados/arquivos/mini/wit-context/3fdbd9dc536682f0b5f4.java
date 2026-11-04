/*
 * @test
 * @summary Test ExplodedSystemModules.moduleReads() throws InternalError as expected
 * @run main ExplodedSystemModulesWitupTest
 */

import jdk.internal.module.ExplodedSystemModules;
import java.util.Map;
import java.util.Set;

public class ExplodedSystemModulesWitupTest {
    public static void main(String[] args) {
        ExplodedSystemModules esm = new ExplodedSystemModules();
        try {
            esm.moduleReads();
            throw new AssertionError("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
