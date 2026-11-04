/* @test    @summary Test getAnnotationsByType method in AccessibleObject class    @run main AccessibleObjectWitupTest */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        testGetAnnotationsByTypeReturnsAssertionError();
    }

    private static void testGetAnnotationsByTypeReturnsAssertionError() {
        AccessibleObject ao = new AccessibleObject() {
            @Override
            public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
                throw new AssertionError("All subclasses should override this method");
            }
        };
        try {
            ao.getAnnotationsByType(Annotation.class);
            // If no exception, test fails
            throw new AssertionError("Expected AssertionError was not thrown");
        } catch (AssertionError e) {
            // Expected exception, test passes
            System.out.println("AssertionError caught as expected: " + e.getMessage());
        }
    }
}
