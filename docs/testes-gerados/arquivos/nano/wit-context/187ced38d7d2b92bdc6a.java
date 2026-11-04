/* @test    @summary Test for Unsafe.defineAnonymousClass handling null parameters    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testDefineAnonymousClassNullHostClass();
        testDefineAnonymousClassNullData();
    }

    private static void testDefineAnonymousClassNullHostClass() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.defineAnonymousClass(null, new byte[0], null);
            System.out.println("FAIL: Expected NullPointerException for null hostClass");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null hostClass");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null hostClass: " + t);
        }
    }

    private static void testDefineAnonymousClassNullData() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.defineAnonymousClass(Object.class, null, null);
            System.out.println("FAIL: Expected NullPointerException for null data");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null data");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null data: " + t);
        }
    }
}
