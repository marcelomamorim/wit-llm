/*
 * @test
 * @summary Test for System.addOpens method to verify behavior with null parameters.
 * @run main SystemWitupTest
 */

import java.lang.Module;
import java.lang.System;

public class SystemWitupTest {

    public static void main(String[] args) {
        testAddOpensWithNullModule();
        testAddOpensWithNullPackageNameAndNonNullOther();
    }

    private static void testAddOpensWithNullModule() {
        try {
            System.class.getMethod("addOpens", Module.class, String.class, Module.class)
                .invoke(new System(), null, "com.example", new Module() {
                    @Override
                    public String getName() { return "dummy"; }
                });
            System.out.println("FAIL: Expected NullPointerException for null Module");
        } catch (Exception e) {
            if (e.getCause() instanceof NullPointerException) {
                System.out.println("PASS: NullPointerException thrown as expected for null Module");
            } else {
                System.out.println("FAIL: Unexpected exception: " + e);
            }
        }
    }

    private static void testAddOpensWithNullPackageNameAndNonNullOther() {
        try {
            Module dummyModule = new Module() {
                @Override
                public String getName() { return "dummy"; }
            };
            System.class.getMethod("addOpens", Module.class, String.class, Module.class)
                .invoke(new System(), dummyModule, null, dummyModule);
            System.out.println("FAIL: Expected NullPointerException for null package name");
        } catch (Exception e) {
            if (e.getCause() instanceof NullPointerException) {
                System.out.println("PASS: NullPointerException thrown as expected for null package name");
            } else {
                System.out.println("FAIL: Unexpected exception: " + e);
            }
        }
    }
}
