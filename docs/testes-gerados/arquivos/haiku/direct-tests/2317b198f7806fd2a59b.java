/*
 * @test
 * @summary Verify TypeVariableImpl.getDeclaredAnnotation null handling and absent-annotation behavior via public TypeVariable API
 * @run main TypeVariableImplWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.TypeVariable;

public class TypeVariableImplWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {}

    static class Holder<T> {}

    public static void main(String[] args) throws Exception {
        TypeVariable<?> tv = Holder.class.getTypeParameters()[0];

        // Null argument must be rejected with NullPointerException (Objects.requireNonNull)
        boolean npeThrown = false;
        try {
            tv.getDeclaredAnnotation((Class<Marker>) null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null annotationClass");
        }

        // Annotation not present on the type variable must yield null
        Marker m = tv.getDeclaredAnnotation(Marker.class);
        if (m != null) {
            throw new RuntimeException("Expected null for absent annotation, got " + m);
        }

        // Repeated call is deterministic
        if (tv.getDeclaredAnnotation(Marker.class) != null) {
            throw new RuntimeException("Expected null on repeated call");
        }

        // Standard annotation absent on type variable must also yield null
        if (tv.getDeclaredAnnotation(Deprecated.class) != null) {
            throw new RuntimeException("Expected null for absent Deprecated annotation");
        }
    }
}
