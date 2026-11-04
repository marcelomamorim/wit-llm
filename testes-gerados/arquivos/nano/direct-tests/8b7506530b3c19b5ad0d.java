/*
 * @test
 * @summary Test addOpensToAllUnnamed method of java.lang.System
 * @run main SystemWitupTest
 */

import java.lang.Module;
import java.lang.System;

public class SystemWitupTest {

    public static void main(String[] args) {
        testAddOpensToAllUnnamedWithValidModule();
        testAddOpensToAllUnnamedWithNullModule();
        testAddOpensToAllUnnamedWithNullPackageName();
    }

    private static void testAddOpensToAllUnnamedWithValidModule() {
        Module module = System.class.getModule();
        String packageName = "java.lang";
        try {
            System system = new System();
            system.addOpensToAllUnnamed(module, packageName);
            System.out.println("testAddOpensToAllUnnamedWithValidModule passed");
        } catch (Throwable t) {
            System.err.println("testAddOpensToAllUnnamedWithValidModule failed: " + t);
        }
    }

    private static void testAddOpensToAllUnnamedWithNullModule() {
        String packageName = "java.lang";
        try {
            System system = new System();
            system.addOpensToAllUnnamed(null, packageName);
            System.out.println("testAddOpensToAllUnnamedWithNullModule passed");
        } catch (NullPointerException e) {
            System.out.println("testAddOpensToAllUnnamedWithNullModule passed with expected NullPointerException");
        } catch (Throwable t) {
            System.err.println("testAddOpensToAllUnnamedWithNullModule failed: " + t);
        }
    }

    private static void testAddOpensToAllUnnamedWithNullPackageName() {
        Module module = System.class.getModule();
        try {
            System system = new System();
            system.addOpensToAllUnnamed(module, null);
            System.out.println("testAddOpensToAllUnnamedWithNullPackageName passed");
        } catch (NullPointerException e) {
            System.out.println("testAddOpensToAllUnnamedWithNullPackageName passed with expected NullPointerException");
        } catch (Throwable t) {
            System.err.println("testAddOpensToAllUnnamedWithNullPackageName failed: " + t);
        }
    }
}
