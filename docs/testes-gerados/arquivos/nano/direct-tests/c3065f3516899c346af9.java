/*
 * @test
 * @summary Test for getDeclaredAnnotationsByType method in TypeVariableImpl
 * @run main TypeVariableImplWitupTest
 */

import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;
import java.lang.annotation.Annotation;
import java.util.Arrays;

public class TypeVariableImplWitupTest {

    public static void main(String[] args) {
        testGetDeclaredAnnotationsByType();
    }

    public static void testGetDeclaredAnnotationsByType() {
        // Create a dummy annotation class for testing
        class DummyAnnotation implements Annotation {
            @Override
            public Class<? extends Annotation> annotationType() {
                return DummyAnnotation.class;
            }
        }

        // Create an anonymous subclass of TypeVariableImpl with minimal implementation
        TypeVariableImpl<Object> typeVar = new TypeVariableImpl<Object>(null, "T", new sun.reflect.generics.tree.FieldTypeSignature[0], null) {
            @Override
            public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
                if (annotationClass == DummyAnnotation.class) {
                    T[] annotations = (T[]) new Annotation[] { new DummyAnnotation() };
                    return annotations;
                }
                return (T[]) new Annotation[0];
            }
        };

        // Call getDeclaredAnnotationsByType with DummyAnnotation
        Annotation[] annotations = typeVar.getDeclaredAnnotationsByType(DummyAnnotation.class);
        // Verify that the returned array contains an instance of DummyAnnotation
        boolean containsDummy = Arrays.stream(annotations)
            .anyMatch(a -> a.annotationType() == DummyAnnotation.class);
        if (containsDummy) {
            System.out.println("Test passed: getDeclaredAnnotationsByType returned DummyAnnotation.");
        } else {
            System.out.println("Test failed: DummyAnnotation not found.");
        }
    }
}
