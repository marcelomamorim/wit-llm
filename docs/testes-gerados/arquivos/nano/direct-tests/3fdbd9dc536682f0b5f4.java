/* @test    @summary Test for moduleReads method in ExplodedSystemModules class    @run main ExplodedSystemModulesWitupTest */

import java.util.Map;
import java.util.Set;
import jdk.internal.module.ExplodedSystemModules;

public class ExplodedSystemModulesWitupTest {

    public static void main(String[] args) {
        testModuleReadsReturnsInternalError();
    }

    private static void testModuleReadsReturnsInternalError() {
        try {
            Map<String, Set<String>> result = new ExplodedSystemModules().moduleReads();
            // Since the method throws InternalError, reaching this line indicates failure
            System.err.println("Expected InternalError was not thrown.");
            System.exit(1);
        } catch (InternalError e) {
            // Expected exception, test passes
            System.out.println("Caught expected InternalError.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
