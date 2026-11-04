/*
 * @test
 * @summary Test java.lang.reflect.Method.getAnnotation(Class<T>) for null argument handling
 * @run main MethodWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodWitupTest {
    public static void main(String[] args) throws Exception {
        // Obtain a Method instance to test getAnnotation
        Method method = MethodWitupTest.class.getMethod("main", String[].class);

        // Test 1: Passing null should throw NullPointerException
        try {
            method.getAnnotation(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test 2: Passing a valid annotation class that is not present should return null
        Annotation ann = method.getAnnotation(Deprecated.class);
        if (ann != null) {
            throw new RuntimeException("Expected null when annotation not present");
        }

        // Test 3: Passing an annotation class that is present
        Deprecated deprecated = MethodWitupTest.class.getAnnotation(Deprecated.class);
        // The class is not deprecated, so expected null
        if (deprecated != null) {
            throw new RuntimeException("Expected null for annotation not present on class");
        }

        System.out.println("All tests passed.");
    }
}
