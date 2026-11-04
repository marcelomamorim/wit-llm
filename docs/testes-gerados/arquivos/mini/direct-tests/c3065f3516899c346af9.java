/*
 * @test
 * @summary Test getDeclaredAnnotationsByType method of TypeVariableImpl
 * @run main TypeVariableImplWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.TypeVariable;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;
import sun.reflect.generics.tree.FieldTypeSignature;
import sun.reflect.generics.factory.GenericsFactory;

public class TypeVariableImplWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String value() default "test";
    }

    // Dummy GenericsFactory implementation for testing
    static class DummyGenericsFactory implements GenericsFactory {
        public java.lang.reflect.Type getType(String name) { return null; }
        public java.lang.reflect.TypeVariable<?> getTypeVariable(String name) { return null; }
        public java.lang.reflect.Type getType(FieldTypeSignature fts) { return null; }
    }

    public static void main(String[] args) throws Exception {
        // Create a TypeVariableImpl instance with minimal parameters
        GenericsFactory factory = new DummyGenericsFactory();
        FieldTypeSignature[] bounds = new FieldTypeSignature[0];

        // Use a Class as genericDeclaration (valid according to make method)
        Class<?> decl = String.class;

        TypeVariableImpl<Class<?>> typeVar = TypeVariableImpl.make(decl, "T", bounds, factory);

        // Test 1: Passing null to getDeclaredAnnotationsByType should throw NullPointerException
        try {
            typeVar.getDeclaredAnnotationsByType(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 2: Passing an annotation class with no annotations present returns empty array
        TestAnnotation[] annotations = typeVar.getDeclaredAnnotationsByType(TestAnnotation.class);
        if (annotations == null) {
            throw new RuntimeException("Returned array is null");
        }
        if (annotations.length != 0) {
            throw new RuntimeException("Expected empty array for annotations but got length " + annotations.length);
        }

        // Test 3: Passing standard annotation class (Override) returns empty array
        Override[] overrideAnnotations = typeVar.getDeclaredAnnotationsByType(Override.class);
        if (overrideAnnotations == null) {
            throw new RuntimeException("Returned array is null");
        }
        if (overrideAnnotations.length != 0) {
            throw new RuntimeException("Expected empty array for Override annotations but got length " + overrideAnnotations.length);
        }

        System.out.println("All tests passed.");
    }
}
