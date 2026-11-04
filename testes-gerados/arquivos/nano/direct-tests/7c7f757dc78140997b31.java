/*
 * @test
 * @summary Test addReadsAllUnnamed method of java.lang.System
 * @run main SystemWitupTest
 */

import java.lang.Module;
import java.lang.System;

public class SystemWitupTest {
    public static void main(String[] args) {
        testAddReadsAllUnnamedWithNull();
        testAddReadsAllUnnamedWithValidModule();
    }

    private static void testAddReadsAllUnnamedWithNull() {
        try {
            System.addReadsAllUnnamed(null);
            System.out.println("Test failed: Expected NullPointerException for null Module");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null Module");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception type: " + t);
        }
    }

    private static void testAddReadsAllUnnamedWithValidModule() {
        try {
            Module module = System.class.getModule();
            System.addReadsAllUnnamed(module);
            System.out.println("Passed: addReadsAllUnnamed invoked successfully with valid Module");
        } catch (Throwable t) {
            System.out.println("Test failed: Exception thrown during valid Module invocation: " + t);
        }
    }
}
