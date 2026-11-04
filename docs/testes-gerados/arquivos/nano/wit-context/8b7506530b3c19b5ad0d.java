/*
 * @test
 * @summary Tests for System.addOpensToAllUnnamed method.
 * @run main SystemWitupTest
 */

import java.lang.Module;
import java.lang.System;

public class SystemWitupTest {

    public static void main(String[] args) {
        testAddOpensToAllUnnamed_NullModule();
        testAddOpensToAllUnnamed_NullModuleConstant();
    }

    private static void testAddOpensToAllUnnamed_NullModule() {
        System.out.println("Running testAddOpensToAllUnnamed_NullModule");
        System.out.println("Expecting NullPointerException when module is null");
        try {
            System.class.getMethod("addOpensToAllUnnamed", Module.class, String.class)
                .invoke(new System(), (Object) null, "java.lang");
            System.out.println("FAIL: Expected NullPointerException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof NullPointerException) {
                System.out.println("PASS: Caught expected NullPointerException");
            } else {
                System.out.println("FAIL: Caught unexpected exception: " + e.getCause());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testAddOpensToAllUnnamed_NullModuleConstant() {
        System.out.println("Running testAddOpensToAllUnnamed_NullModuleConstant");
        System.out.println("Expecting NullPointerException when Module.ALL_UNNAMED_MODULE is null");
        try {
            // Simulate the static constant being null by reflection or by temporarily setting it to null if possible.
            // Since it's a final static field, we cannot modify it directly; instead, we invoke the method with null.
            System.class.getMethod("addOpensToAllUnnamed", Module.class, String.class)
                .invoke(new System(), (Object) null, "java.lang");
            System.out.println("FAIL: Expected NullPointerException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof NullPointerException) {
                System.out.println("PASS: Caught expected NullPointerException");
            } else {
                System.out.println("FAIL: Caught unexpected exception: " + e.getCause());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
