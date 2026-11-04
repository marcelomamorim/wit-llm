/*
 * @test
 * @summary Test for System.addReadsAllUnnamed(Module m)
 * @run main SystemWitupTest
 */

import java.lang.Module;
import java.lang.System;

public class SystemWitupTest {

    public static void main(String[] args) {
        testAddReadsAllUnnamed_NullModule();
        testAddReadsAllUnnamed_NonNullModule();
    }

    private static void testAddReadsAllUnnamed_NullModule() {
        try {
            System.out.println("Test addReadsAllUnnamed with null Module");
            System systemInstance = new System();
            systemInstance.addReadsAllUnnamed(null);
            System.out.println("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException");
        } catch (Throwable t) {
            System.out.println("Caught unexpected exception: " + t);
        }
    }

    private static void testAddReadsAllUnnamed_NonNullModule() {
        try {
            System.out.println("Test addReadsAllUnnamed with non-null Module");
            Module m = ModuleLayer.boot().modules().iterator().next();
            System systemInstance = new System();
            systemInstance.addReadsAllUnnamed(m);
            System.out.println("Method invoked successfully with non-null Module");
        } catch (Throwable t) {
            System.out.println("Exception during non-null Module test: " + t);
        }
    }
}
