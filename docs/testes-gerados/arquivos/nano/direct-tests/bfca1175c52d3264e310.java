/* @test    @summary Test for ExplodedSystemModules.moduleResolutions() method to ensure it throws InternalError as expected.    @run main ExplodedSystemModulesWitupTest */

import jdk.internal.module.ExplodedSystemModules;

public class ExplodedSystemModulesWitupTest {

    public static void main(String[] args) {
        testModuleResolutionsThrowsInternalError();
    }

    public static void testModuleResolutionsThrowsInternalError() {
        ExplodedSystemModules esm = new ExplodedSystemModules();
        try {
            esm.moduleResolutions();
            System.out.println("FAIL: Expected InternalError was not thrown.");
        } catch (InternalError e) {
            System.out.println("PASS: InternalError thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
