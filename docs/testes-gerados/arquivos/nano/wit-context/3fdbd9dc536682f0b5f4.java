/*
 * @test
 * @summary Test for jdk.internal.module.ExplodedSystemModules.moduleReads()
 * @run main ExplodedSystemModulesWitupTest
 */

import java.util.Map;
import java.util.Set;
import jdk.internal.module.ExplodedSystemModules;

public class ExplodedSystemModulesWitupTest {

    public static void main(String[] args) {
        testModuleReadsReturnsInternalError();
    }

    public static void testModuleReadsReturnsInternalError() {
        ExplodedSystemModules esm = new ExplodedSystemModules();
        try {
            esm.moduleReads();
            System.out.println("FAIL: Expected InternalError was not thrown.");
        } catch (InternalError e) {
            System.out.println("PASS: Caught expected InternalError.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
