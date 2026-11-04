/* @test    @summary Test getAnnotation method in AccessibleObject class    @run main AccessibleObjectWitupTest */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        testGetAnnotationThrowsAssertionError();
    }

    private static void testGetAnnotationThrowsAssertionError() {
        AccessibleObject ao = new AccessibleObject() {
            @Override
            public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
                throw new AssertionError("All subclasses should override this method");
            }
        };
        try {
            ao.getAnnotation(Annotation.class);
            // If no exception, test fails
            throw new AssertionError("Expected AssertionError was not thrown");
        } catch (AssertionError e) {
            // Expected exception, test passes
        }
    }
}
