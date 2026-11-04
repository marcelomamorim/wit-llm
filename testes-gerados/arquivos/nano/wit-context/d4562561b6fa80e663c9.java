/*
 * @test
 * @summary Test for EnumSet methods, focusing on exception paths and behavior.
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;
import java.lang.IllegalArgumentException;

public class EnumSetWitupTest {

    public static void main(String[] args) {
        testAllOfWithNull();
        testOfWithNull();
        testOfMultipleEnums();
    }

    public static void testAllOfWithNull() {
        try {
            EnumSet.allOf(null);
            System.out.println("FAIL: Expected NullPointerException for allOf(null)");
        } catch (NullPointerException expected) {
            System.out.println("PASS: Caught expected NullPointerException for allOf(null)");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for allOf(null): " + t);
        }
    }

    public static void testOfWithNull() {
        try {
            EnumSet.of(null);
            System.out.println("FAIL: Expected NullPointerException for of(null)");
        } catch (NullPointerException expected) {
            System.out.println("PASS: Caught expected NullPointerException for of(null)");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for of(null): " + t);
        }
    }

    public static void testOfMultipleEnums() {
        // Using a dummy enum for testing
        enum TestEnum {A, B, C}
        try {
            EnumSet.of(TestEnum.A, TestEnum.B, null);
            System.out.println("FAIL: Expected NullPointerException for of with null element");
        } catch (NullPointerException expected) {
            System.out.println("PASS: Caught expected NullPointerException for of with null element");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for of with null element: " + t);
        }
        // Valid case
        try {
            EnumSet<TestEnum> set = EnumSet.of(TestEnum.A, TestEnum.B);
            if (set.contains(TestEnum.A) && set.contains(TestEnum.B)) {
                System.out.println("PASS: EnumSet.of with multiple enums works as expected");
            } else {
                System.out.println("FAIL: EnumSet.of with multiple enums does not contain expected elements");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception in valid of multiple enums: " + t);
        }
    }
}
