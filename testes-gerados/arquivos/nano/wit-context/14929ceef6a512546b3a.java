/*
 * @test
 * @summary Test for MethodHandles.unreflectVarHandle(Field f)
 * @run main MethodHandlesWitupTest
 */

import java.lang.reflect.Field;
import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testUnreflectVarHandle_NullField();
        testUnreflectVarHandle_ValidField();
    }

    private static void testUnreflectVarHandle_NullField() {
        try {
            MethodHandles.unreflectVarHandle(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }

    private static void testUnreflectVarHandle_ValidField() {
        try {
            // Use a known public static field for testing
            Field field = String.class.getDeclaredField("CASE_INSENSITIVE_ORDER");
            VarHandle vh = MethodHandles.unreflectVarHandle(field);
            if (vh == null) {
                System.err.println("VarHandle is null");
                System.exit(1);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
