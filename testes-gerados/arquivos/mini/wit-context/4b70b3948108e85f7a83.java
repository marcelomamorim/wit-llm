/*
 * @test
 * @summary Test java.lang.reflect.Field.getAnnotationsByType for null argument and normal usage
 * @run main FieldWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class FieldWitupTest {

    public static void main(String[] args) throws Exception {
        testGetAnnotationsByTypeNullArgument();
        testGetAnnotationsByTypeNormal();
        System.out.println("All tests passed.");
    }

    static void testGetAnnotationsByTypeNullArgument() throws Exception {
        Field f = Sample.class.getDeclaredField("value");
        try {
            f.getAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGetAnnotationsByTypeNormal() throws Exception {
        Field f = Sample.class.getDeclaredField("value");
        Deprecated[] annos = f.getAnnotationsByType(Deprecated.class);
        if (annos == null) {
            throw new RuntimeException("Returned null instead of empty array");
        }
        // The field is annotated with @Deprecated, so array length should be 1
        if (annos.length != 1) {
            throw new RuntimeException("Expected exactly one Deprecated annotation, got " + annos.length);
        }
        if (!annos[0].annotationType().equals(Deprecated.class)) {
            throw new RuntimeException("Returned annotation is not Deprecated");
        }
    }

    static class Sample {
        @Deprecated
        int value;
    }
}
