/*
 * @test    @summary Test getDeclaredAnnotation method of TypeVariableImpl
 * @run main TypeVariableImplWitupTest
 */

import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;
import java.lang.annotation.Annotation;
import java.util.Objects;

public class TypeVariableImplWitupTest {

    public static void main(String[] args) {
        testGetDeclaredAnnotationReturnsCorrectAnnotation();
        testGetDeclaredAnnotationWithNull();
    }

    private static void testGetDeclaredAnnotationReturnsCorrectAnnotation() {
        class Sample {
            @Deprecated
            void method() {}
        }
        try {
            // Obtain the Method object for 'method' in Sample class
            java.lang.reflect.Method method = Sample.class.getDeclaredMethod("method");
            // Create a TypeVariableImpl instance for demonstration purposes
            // Note: In real scenario, this would be obtained via reflection internals
            // Here, we simulate by creating an anonymous subclass with the annotation
            TypeVariableImpl<Object> typeVar = new TypeVariableImpl<>(null, "T", null, null) {
                @Override
                public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
                    if (annotationClass == Deprecated.class) {
                        return (T) Deprecated.class.getAnnotation(annotationClass);
                    }
                    return null;
                }
            };
            // Call getDeclaredAnnotation with Deprecated class
            Deprecated dep = typeVar.getDeclaredAnnotation(Deprecated.class);
            assert dep != null : "Expected Deprecated annotation to be returned";
            System.out.println("getDeclaredAnnotation returns Deprecated as expected.");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testGetDeclaredAnnotationWithNull() {
        class Sample {
        }
        try {
            TypeVariableImpl<Object> typeVar = new TypeVariableImpl<>(null, "T", null, null);
            // Expect NullPointerException when passing null
            typeVar.getDeclaredAnnotation(null);
            throw new AssertionError("Expected NullPointerException for null annotationClass");
        } catch (NullPointerException e) {
            System.out.println("getDeclaredAnnotation throws NullPointerException as expected.");
        }
    }
}
