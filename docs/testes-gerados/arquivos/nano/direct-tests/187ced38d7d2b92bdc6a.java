/* @test    @summary Test defineAnonymousClass method of Unsafe class    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testDefineAnonymousClassWithNullHostClass();
        testDefineAnonymousClassWithNullData();
        testDefineAnonymousClassWithArrayHostClass();
        testDefineAnonymousClassWithPrimitiveHostClass();
        testDefineAnonymousClassWithValidInputs();
    }

    private static void testDefineAnonymousClassWithNullHostClass() {
        try {
            Unsafe.getUnsafe().defineAnonymousClass(null, new byte[]{0x00}, null);
            System.out.println("Failed: Expected NullPointerException for null hostClass");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException for null hostClass");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception for null hostClass: " + t);
        }
    }

    private static void testDefineAnonymousClassWithNullData() {
        try {
            Class<?> dummyClass = Object.class;
            Unsafe.getUnsafe().defineAnonymousClass(dummyClass, null, null);
            System.out.println("Failed: Expected NullPointerException for null data");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException for null data");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception for null data: " + t);
        }
    }

    private static void testDefineAnonymousClassWithArrayHostClass() {
        try {
            Class<?> arrayClass = int[].class;
            Unsafe.getUnsafe().defineAnonymousClass(arrayClass, new byte[]{0x00}, null);
            System.out.println("Failed: Expected IllegalArgumentException for array hostClass");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException for array hostClass");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception for array hostClass: " + t);
        }
    }

    private static void testDefineAnonymousClassWithPrimitiveHostClass() {
        try {
            Class<?> primitiveClass = int.class;
            Unsafe.getUnsafe().defineAnonymousClass(primitiveClass, new byte[]{0x00}, null);
            System.out.println("Failed: Expected IllegalArgumentException for primitive hostClass");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException for primitive hostClass");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception for primitive hostClass: " + t);
        }
    }

    private static void testDefineAnonymousClassWithValidInputs() {
        try {
            Class<?> hostClass = Object.class;
            byte[] data = {0x00};
            Object[] patches = null;
            Class<?> result = Unsafe.getUnsafe().defineAnonymousClass(hostClass, data, patches);
            if (result != null) {
                System.out.println("Passed: defineAnonymousClass returned class: " + result.getName());
            } else {
                System.out.println("Failed: defineAnonymousClass returned null");
            }
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception in valid case: " + t);
        }
    }
}
