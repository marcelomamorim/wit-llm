/*
 * @test
 * @summary Test getAnnotation method of java.lang.reflect.Field
 * @run main FieldWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FieldWitupTest {

    public static void main(String[] args) throws Exception {
        testGetAnnotation();
    }

    public static void testGetAnnotation() throws Exception {
        class AnnotatedClass {
            @Deprecated
            public int annotatedField;
        }
        Field field = AnnotatedClass.class.getField("annotatedField");
        Annotation annotation = field.getAnnotation(Deprecated.class);
        if (annotation == null) {
            throw new AssertionError("Expected Deprecated annotation to be present");
        }
        if (!(annotation instanceof Deprecated)) {
            throw new AssertionError("Annotation is not instance of Deprecated");
        }
        Deprecated dep = (Deprecated) annotation;
        if (dep != null) {
            System.out.println("Deprecated annotation retrieved successfully.");
        } else {
            throw new AssertionError("Failed to retrieve Deprecated annotation");
        }
    }
}
