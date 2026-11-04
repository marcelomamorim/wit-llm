/*
 * @test    @summary Test getAnnotationsByType method in java.lang.reflect.Field
 * @run main FieldWitupTest
 */

import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import java.util.Arrays;

public class FieldWitupTest {

    public static void main(String[] args) {
        testGetAnnotationsByTypeWithAnnotations();
        testGetAnnotationsByTypeWithoutAnnotations();
        testGetAnnotationsByTypeNull();
    }

    static void testGetAnnotationsByTypeWithAnnotations() {
        try {
            // Create a dummy class with annotated field
            class Dummy {
                @Deprecated
                public int annotatedField;
            }
            Field field = Dummy.class.getField("annotatedField");
            Annotation[] annotations = field.getAnnotationsByType(Deprecated.class);
            // Verify that the annotation array contains Deprecated
            boolean containsDeprecated = false;
            for (Annotation a : annotations) {
                if (a.annotationType() == Deprecated.class) {
                    containsDeprecated = true;
                    break;
                }
            }
            if (!containsDeprecated) {
                throw new AssertionError("Expected Deprecated annotation in annotations array");
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    static void testGetAnnotationsByTypeWithoutAnnotations() {
        try {
            class Dummy {
                public int noAnnotations;
            }
            Field field = Dummy.class.getField("noAnnotations");
            Annotation[] annotations = field.getAnnotationsByType(Deprecated.class);
            if (annotations.length != 0) {
                throw new AssertionError("Expected empty annotations array");
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    static void testGetAnnotationsByTypeNull() {
        try {
            class Dummy {
                public int someField;
            }
            Field field = Dummy.class.getField("someField");
            field.getAnnotationsByType(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
