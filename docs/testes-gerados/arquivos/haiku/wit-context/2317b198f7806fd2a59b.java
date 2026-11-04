/*
 * @test
 * @summary Verifies TypeVariable.getDeclaredAnnotation null handling and absent-annotation behavior through public reflection API
 * @run main TypeVariableImplWitupTest
 */
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.TypeVariable;

public class TypeVariableImplWitupTest {
    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {}

    static class Holder<T> {}

    public static void main(String[] args) {
        TypeVariable<?> tv = Holder.class.getTypeParameters()[0];

        boolean npeThrown = false;
        try {
            tv.getDeclaredAnnotation(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null annotationClass");
        }

        if (tv.getDeclaredAnnotation(Marker.class) != null) {
            throw new AssertionError("Expected null for absent annotation Marker");
        }
        if (tv.getDeclaredAnnotation(Retention.class) != null) {
            throw new AssertionError("Expected null for absent annotation Retention");
        }
    }
}
