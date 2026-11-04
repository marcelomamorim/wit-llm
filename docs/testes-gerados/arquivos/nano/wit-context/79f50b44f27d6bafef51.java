/* @test    @summary Test for method moduleTargets() in ExplodedSystemModules class
 * @run main ExplodedSystemModulesWitupTest
 */

import jdk.internal.module.ExplodedSystemModules;

public class ExplodedSystemModulesWitupTest {

    public static void main(String[] args) {
        testModuleTargets();
    }

    public static void testModuleTargets() {
        ExplodedSystemModules esm = new ExplodedSystemModules();
        try {
            esm.moduleTargets();
            // Espera-se que lance InternalError
            System.out.println("FAIL: Expected InternalError was not thrown.");
        } catch (InternalError e) {
            System.out.println("PASS: Caught expected InternalError.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
