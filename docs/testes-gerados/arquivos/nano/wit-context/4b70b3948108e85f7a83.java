/*
 * @test
 * @summary Test for java.lang.reflect.Field.getAnnotationsByType handling null annotationClass
 * @run main FieldWitupTest
 */

import java.lang.reflect.Field;
import java.lang.annotation.Annotation;

public class FieldWitupTest {

    public static void main(String[] args) {
        testGetAnnotationsByTypeWithNull();
        testGetAnnotationsByTypeWithValidClass();
    }

    private static void testGetAnnotationsByTypeWithNull() {
        try {
            Field field = null; // No need to instantiate actual Field, focus on method call
            // Using reflection to invoke method with null argument
            // Since we cannot instantiate Field directly, we simulate the call via reflection
            // but as per constraints, we test the method directly with null
            // So, create a dummy subclass or use reflection to invoke method if possible
            // For simplicity, assume we have a Field instance (simulate with null, expecting NullPointerException)
            // But to avoid null pointer, create a dummy subclass or mock, which is complex.
            // Instead, test the method with a real Field object and null argument.
            // For that, get a real Field from a class.
            class Dummy {
                public int value;
            }
            Field dummyField = Dummy.class.getDeclaredField("value");
            // Now, invoke getAnnotationsByType with null
            try {
                dummyField.getAnnotationsByType(null);
                System.out.println("FAIL: Expected NullPointerException not thrown");
            } catch (NullPointerException e) {
                System.out.println("PASS: NullPointerException thrown as expected");
            }
        } catch (NoSuchFieldException e) {
            System.out.println("FAIL: Unexpected NoSuchFieldException: " + e);
        }
    }

    private static void testGetAnnotationsByTypeWithValidClass() {
        try {
            class Dummy {
                @Deprecated
                public int value;
            }
            Field field = Dummy.class.getDeclaredField("value");
            // Call getAnnotationsByType with Deprecated class
            Object[] annotations = field.getAnnotationsByType(Deprecated.class);
            boolean hasDeprecated = false;
            for (Object ann : annotations) {
                if (ann instanceof Deprecated) {
                    hasDeprecated = true;
                }
            }
            if (hasDeprecated) {
                System.out.println("PASS: Deprecated annotation found as expected");
            } else {
                System.out.println("FAIL: Deprecated annotation not found");
            }
        } catch (NoSuchFieldException e) {
            System.out.println("FAIL: Unexpected NoSuchFieldException: " + e);
        }
    }
}
