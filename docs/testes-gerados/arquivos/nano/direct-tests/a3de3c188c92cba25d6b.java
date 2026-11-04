/* @test    @summary Test for ConstantBootstraps.primitiveClass method    @run main ConstantBootstrapsWitupTest */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {

    public static void main(String[] args) {
        testPrimitiveClassValid();
        testPrimitiveClassInvalidType();
        testPrimitiveClassInvalidNameEmpty();
        testPrimitiveClassInvalidNameLong();
    }

    static void testPrimitiveClassValid() {
        try {
            Class<?> result = ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "I", Class.class);
            if (result != int.class) {
                throw new AssertionError("Expected int.class, got " + result);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in testPrimitiveClassValid", t);
        }
    }

    static void testPrimitiveClassInvalidType() {
        try {
            ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "I", String.class);
            throw new AssertionError("Expected IllegalArgumentException for non-Class type");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in testPrimitiveClassInvalidType", t);
        }
    }

    static void testPrimitiveClassInvalidNameEmpty() {
        try {
            ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "", Class.class);
            throw new AssertionError("Expected IllegalArgumentException for empty name");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in testPrimitiveClassInvalidNameEmpty", t);
        }
    }

    static void testPrimitiveClassInvalidNameLong() {
        try {
            ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "AB", Class.class);
            throw new AssertionError("Expected IllegalArgumentException for long name");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in testPrimitiveClassInvalidNameLong", t);
        }
    }
}
