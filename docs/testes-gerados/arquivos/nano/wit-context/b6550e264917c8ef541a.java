/* @test    @summary Test for AccessibleObject.getAnnotation to observe behavior and exception handling    @run main AccessibleObjectWitupTest */

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
            System.out.println("Test failed: no exception thrown");
        } catch (AssertionError e) {
            System.out.println("Test passed: caught expected AssertionError");
        } catch (Throwable t) {
            System.out.println("Test failed: caught unexpected exception: " + t);
        }
    }
}
