/*
 * @test
 * @summary Test for Class.getAnnotationsByType with null argument
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.Class;

public class ClassWitupTest {

    public static void main(String[] args) {
        testGetAnnotationsByTypeNull();
        testGetAnnotationsByTypeValid();
    }

    private static void testGetAnnotationsByTypeNull() {
        Class<?> clazz = Class.class;
        try {
            clazz.getAnnotationsByType(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }

    private static void testGetAnnotationsByTypeValid() {
        Class<?> clazz = String.class;
        try {
            Annotation[] annotations = clazz.getAnnotationsByType(Deprecated.class);
            System.out.println("Annotations returned: " + annotations.length);
        } catch (Throwable t) {
            System.err.println("Unexpected exception during valid call: " + t);
            System.exit(1);
        }
    }
}
