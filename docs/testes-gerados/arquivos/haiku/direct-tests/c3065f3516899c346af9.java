/*
 * @test
 * @summary Verify TypeVariableImpl.getDeclaredAnnotationsByType via the public TypeVariable API
 * @run main TypeVariableImplWitupTest
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.TypeVariable;

public class TypeVariableImplWitupTest<T> {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE_PARAMETER)
    @interface Tag {
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TypeVariable<?>[] typeVars = TypeVariableImplWitupTest.class.getTypeParameters();
        check(typeVars.length == 1, "expected one type variable");
        TypeVariable<?> tv = typeVars[0];

        boolean npeThrown = false;
        try {
            tv.getDeclaredAnnotationsByType(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "null annotationClass must throw NullPointerException");

        Tag[] tags = tv.getDeclaredAnnotationsByType(Tag.class);
        check(tags != null, "result must not be null");
        check(tags.length == 0, "unannotated type variable must yield no annotations");
        check(tags.getClass().getComponentType() == Tag.class,
              "result array component type must be the requested annotation class");
    }
}
