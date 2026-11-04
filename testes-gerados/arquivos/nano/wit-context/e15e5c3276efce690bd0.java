/* @test    @summary Test for AccessibleObject.getAnnotationsByType to observe AssertionError behavior    @run main AccessibleObjectWitupTest */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        testGetAnnotationsByTypeThrowsAssertionError();
    }

    private static void testGetAnnotationsByTypeThrowsAssertionError() {
        AccessibleObject ao = new AccessibleObject() {
            @Override
            public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
                throw new AssertionError("All subclasses should override this method");
            }
        };
        try {
            ao.getAnnotationsByType(Annotation.class);
            System.out.println("Test failed: no exception thrown");
        } catch (AssertionError e) {
            System.out.println("Test passed: caught expected AssertionError");
        } catch (Throwable t) {
            System.out.println("Test failed: caught unexpected exception: " + t);
        }
    }
}
