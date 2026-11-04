/*
 * @test
 * @summary Test unreflectVarHandle method for various Field inputs.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.IllegalAccessException;
import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Exception {
        testUnreflectVarHandleWithAccessibleField();
        testUnreflectVarHandleWithNullField();
        testUnreflectVarHandleWithInvalidField();
    }

    static void testUnreflectVarHandleWithAccessibleField() throws Exception {
        Field field = SampleClass.class.getDeclaredField("sampleField");
        VarHandle vh = MethodHandles.unreflectVarHandle(field);
        if (vh == null) {
            throw new AssertionError("VarHandle should not be null for accessible field");
        }
        System.out.println("Accessible field test passed.");
    }

    static void testUnreflectVarHandleWithNullField() {
        try {
            MethodHandles.unreflectVarHandle(null);
            throw new AssertionError("Expected NullPointerException for null Field");
        } catch (NullPointerException e) {
            System.out.println("Null field test passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testUnreflectVarHandleWithInvalidField() {
        try {
            // Create a dummy Field object via reflection (simulate invalid field)
            Field invalidField = DummyFieldHolder.class.getDeclaredField("dummy");
            MethodHandles.unreflectVarHandle(invalidField);
            throw new AssertionError("Expected IllegalAccessException for invalid field");
        } catch (IllegalAccessException e) {
            System.out.println("Invalid field access test passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static class SampleClass {
        public int sampleField;
    }

    static class DummyFieldHolder {
        private static final Field dummy;
        static {
            try {
                dummy = DummyFieldHolder.class.getDeclaredField("nonExistent");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
