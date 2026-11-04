/*
 * @test
 * @summary Checks TypeVariableImpl.getDeclaredAnnotationsByType null handling and empty result for an unannotated type variable
 * @run main TypeVariableImplWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.TypeVariable;

public class TypeVariableImplWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE_PARAMETER)
    @interface Marker {
        String value() default "";
    }

    static class Plain<T> {
    }

    public static void main(String[] args) throws Exception {
        TypeVariable<?> tv = Plain.class.getTypeParameters()[0];

        boolean npeThrown = false;
        try {
            tv.getDeclaredAnnotationsByType(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null annotationClass");
        }

        Marker[] result = tv.getDeclaredAnnotationsByType(Marker.class);
        if (result == null) {
            throw new RuntimeException("Expected non-null array");
        }
        if (result.length != 0) {
            throw new RuntimeException("Expected empty array for unannotated type variable, got length "
                    + result.length);
        }
    }
}
