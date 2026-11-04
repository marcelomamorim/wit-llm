/* @test    @summary Test for method jdk.internal.module.ExplodedSystemModules.moduleResolutions() to verify it throws InternalError as expected.    @run main ExplodedSystemModulesWitupTest */

import jdk.internal.module.ExplodedSystemModules;

public class ExplodedSystemModulesWitupTest {

    public static void main(String[] args) {
        testModuleResolutionsThrowsInternalError();
    }

    public static void testModuleResolutionsThrowsInternalError() {
        try {
            ExplodedSystemModules esm = new ExplodedSystemModules();
            esm.moduleResolutions();
            System.out.println("FAIL: Expected InternalError was not thrown.");
        } catch (InternalError e) {
            System.out.println("PASS: Caught expected InternalError.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
