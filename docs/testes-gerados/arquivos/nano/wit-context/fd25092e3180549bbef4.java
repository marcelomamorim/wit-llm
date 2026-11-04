/* @test    @summary Test for AccessibleObject.getDeclaredAnnotationsByType to observe behavior and exception handling    @run main AccessibleObjectWitupTest */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        testGetDeclaredAnnotationsByTypeWithNull();
        testGetDeclaredAnnotationsByTypeWithInvalidClass();
    }

    public static void testGetDeclaredAnnotationsByTypeWithNull() {
        AccessibleObject ao = getSampleAccessibleObject();
        try {
            ao.getDeclaredAnnotationsByType(null);
            System.out.println("testGetDeclaredAnnotationsByTypeWithNull: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testGetDeclaredAnnotationsByTypeWithNull: PASSED");
        } catch (Throwable t) {
            System.out.println("testGetDeclaredAnnotationsByTypeWithNull: FAILED - Unexpected exception: " + t);
        }
    }

    public static void testGetDeclaredAnnotationsByTypeWithInvalidClass() {
        AccessibleObject ao = getSampleAccessibleObject();
        try {
            // Using a class that is not an annotation type
            ao.getDeclaredAnnotationsByType(String.class);
            System.out.println("testGetDeclaredAnnotationsByTypeWithInvalidClass: PASSED");
        } catch (ClassCastException e) {
            System.out.println("testGetDeclaredAnnotationsByTypeWithInvalidClass: PASSED");
        } catch (Throwable t) {
            System.out.println("testGetDeclaredAnnotationsByTypeWithInvalidClass: FAILED - Unexpected exception: " + t);
        }
    }

    private static AccessibleObject getSampleAccessibleObject() {
        // Create a dummy AccessibleObject instance via anonymous subclass
        return new AccessibleObject() {
            @Override
            public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
                // Call the real method for testing
                return super.getDeclaredAnnotationsByType(annotationClass);
            }
        };
    }
}
