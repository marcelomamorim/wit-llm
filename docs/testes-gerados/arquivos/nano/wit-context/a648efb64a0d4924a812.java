/*
 * @test
 * @summary Test for Executable.getAnnotationsByType with null argument
 * @run main ExecutableWitupTest
 */

import java.lang.reflect.Executable;
import java.lang.annotation.Annotation;

public class ExecutableWitupTest {

    public static void main(String[] args) {
        testGetAnnotationsByTypeNull();
        testGetAnnotationsByTypeValid();
    }

    private static void testGetAnnotationsByTypeNull() {
        try {
            Executable exec = getSampleExecutable();
            exec.getAnnotationsByType(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testGetAnnotationsByTypeValid() {
        try {
            Executable exec = getSampleExecutable();
            Annotation[] annotations = exec.getAnnotationsByType(SampleAnnotation.class);
            if (annotations != null) {
                System.out.println("PASS: getAnnotationsByType returned annotations");
            } else {
                System.out.println("FAIL: getAnnotationsByType returned null");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static Executable getSampleExecutable() {
        // Use reflection to get a sample Executable, e.g., a method from this class
        try {
            return Executable.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    // Dummy annotation for testing
    public @interface SampleAnnotation {}
}
