/*
 * @test
 * @summary Test for MethodHandles.unreflectSetter(Field f) to verify behavior with null input.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testUnreflectSetterWithNull();
    }

    private static void testUnreflectSetterWithNull() {
        try {
            MethodHandles.unreflectSetter(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
