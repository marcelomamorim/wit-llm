/*
 * @test
 * @summary Test sun.reflect.generics.reflectiveObjects.TypeVariableImpl.getDeclaredAnnotation behavior
 * @run main TypeVariableImplWitupTest
 */

import java.lang.annotation.Annotation;
import java.util.Objects;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

public class TypeVariableImplWitupTest {

    // Minimal stub class to allow instantiation of TypeVariableImpl via reflection
    // but since constructor is private and no public factory for this test,
    // we test only getDeclaredAnnotation behavior on a subclass stub.
    static class DummyTypeVariableImpl extends TypeVariableImpl<Class<?>> {
        DummyTypeVariableImpl() {
            super(null, "T", null, null);
        }

        // Override getAnnotation to return null for any input
        @Override
        public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
            return null;
        }
    }

    public static void main(String[] args) {
        DummyTypeVariableImpl typeVar = new DummyTypeVariableImpl();

        // Test 1: Passing null to getDeclaredAnnotation should throw NullPointerException
        try {
            typeVar.getDeclaredAnnotation(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: Passing a valid annotation class returns null (as per stub)
        Annotation result = typeVar.getDeclaredAnnotation(Override.class);
        if (result != null) {
            throw new RuntimeException("Expected null annotation result");
        }

        System.out.println("All tests passed.");
    }
}
