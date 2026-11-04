/* @test    @summary Test getDeclaredAnnotationsByType method in AccessibleObject class    @run main AccessibleObjectWitupTest */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        testGetDeclaredAnnotationsByTypeReturnsAnnotations();
        testGetDeclaredAnnotationsByTypeWithNoAnnotations();
    }

    static void testGetDeclaredAnnotationsByTypeReturnsAnnotations() {
        class TestAnnotation implements Annotation {
            @Override
            public Class<? extends Annotation> annotationType() {
                return TestAnnotation.class;
            }
        }
        class AnnotatedClass {
            @TestAnnotation
            public void annotatedMethod() {}
        }
        try {
            AccessibleObject ao = AnnotatedClass.class.getDeclaredMethod("annotatedMethod");
            Annotation[] annotations = ao.getDeclaredAnnotationsByType(TestAnnotation.class);
            boolean found = false;
            for (Annotation a : annotations) {
                if (a.annotationType() == TestAnnotation.class) {
                    found = true;
                }
            }
            if (!found) {
                throw new AssertionError("Expected annotation not found");
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    static void testGetDeclaredAnnotationsByTypeWithNoAnnotations() {
        class EmptyClass {
            public void method() {}
        }
        try {
            AccessibleObject ao = EmptyClass.class.getDeclaredMethod("method");
            Annotation[] annotations = ao.getDeclaredAnnotationsByType(TestAnnotation.class);
            if (annotations.length != 0) {
                throw new AssertionError("Expected no annotations, but found some");
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
