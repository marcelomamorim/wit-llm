/* @test    @summary Test ExplodedSystemModules.moduleTargets() method for InternalError exception handling    @run main ExplodedSystemModulesWitupTest */

import jdk.internal.module.ExplodedSystemModules;

public class ExplodedSystemModulesWitupTest {

    public static void main(String[] args) {
        testModuleTargetsThrowsInternalError();
    }

    public static void testModuleTargetsThrowsInternalError() {
        try {
            ExplodedSystemModules esm = new ExplodedSystemModules();
            esm.moduleTargets();
            System.out.println("FAIL: Expected InternalError was not thrown.");
        } catch (InternalError e) {
            System.out.println("PASS: Caught expected InternalError.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
