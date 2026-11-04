/*
 * @test
 * @summary Test for java.lang.reflect.Field.getAnnotation method, focusing on null input handling.
 * @run main FieldWitupTest
 */

import java.lang.reflect.Field;
import java.lang.annotation.Annotation;

public class FieldWitupTest {

    public static void main(String[] args) {
        testGetAnnotationWithNull();
        testGetAnnotationWithValidClass();
    }

    public static void testGetAnnotationWithNull() {
        try {
            Field dummyField = null; // Placeholder, as actual Field instance requires reflection setup.
            // Since creating a real Field instance is complex, we simulate the behavior by calling the method with null.
            // Expect NullPointerException due to Objects.requireNonNull
            // To invoke getAnnotation, we need an actual Field object; here, we test the null guard.
            // Alternatively, we can test a real Field object obtained via reflection.
            // For simplicity, we assume the method throws NullPointerException when annotationClass is null.
            // As we cannot instantiate Field directly, we test the method via reflection.
            // But since the method is an instance method, we need a real Field.
            // Instead, we can create a dummy subclass or use reflection to get a real Field.
            // For this test, we will use reflection to get a real Field from this class.
            Field testField = FieldWitupTest.class.getDeclaredField("dummyField");
            // Now, invoke getAnnotation with null
            testField.getClass().getMethod("getAnnotation", Class.class).invoke(testField, (Object) null);
            // The above line will throw NullPointerException, which we catch below.
            System.out.println("FAIL: Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testGetAnnotationWithValidClass() {
        try {
            // Obtain a real Field object, e.g., this class's dummyField
            Field field = FieldWitupTest.class.getDeclaredField("dummyField");
            // Call getAnnotation with a class that does not exist, expect null
            Annotation annotation = field.getAnnotation(Deprecated.class);
            if (annotation == null) {
                System.out.println("PASS: getAnnotation returned null for non-present annotation.");
            } else {
                System.out.println("FAIL: getAnnotation returned unexpected annotation.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during test: " + e);
        }
    }

    // Dummy field for reflection
    private int dummyField;
}
