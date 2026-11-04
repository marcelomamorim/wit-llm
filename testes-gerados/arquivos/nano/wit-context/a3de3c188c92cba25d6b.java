/* @test    @summary Test for ConstantBootstraps.primitiveClass method to verify exception paths and behavior    @run main ConstantBootstrapsWitupTest */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {

    public static void main(String[] args) {
        testNullNameThrowsNullPointerException();
        testNullTypeThrowsNullPointerException();
        testInvalidTypeThrowsIllegalArgumentException();
        testValidPrimitiveTypeReturnsCorrectClass();
    }

    private static void testNullNameThrowsNullPointerException() {
        try {
            ConstantBootstraps.primitiveClass(MethodHandles.lookup(), null, String.class);
            System.err.println("Failed: Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type for null name: " + t);
        }
    }

    private static void testNullTypeThrowsNullPointerException() {
        try {
            ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "i", null);
            System.err.println("Failed: Expected NullPointerException for null type");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type for null type: " + t);
        }
    }

    private static void testInvalidTypeThrowsIllegalArgumentException() {
        try {
            ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "i", String.class);
            System.err.println("Failed: Expected IllegalArgumentException for non-primitive type");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type for invalid type: " + t);
        }
    }

    private static void testValidPrimitiveTypeReturnsCorrectClass() {
        try {
            Class<?> cls = ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "i", int.class);
            if (cls != int.class) {
                System.err.println("Failed: Expected int.class, got " + cls);
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for valid primitive type: " + t);
        }
    }
}
