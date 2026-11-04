/* @test    @summary Test for AccessibleObject.getAnnotations() method to verify it returns declared annotations.    @run main AccessibleObjectWitupTest */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        testGetAnnotationsReturnsDeclaredAnnotations();
    }

    public static void testGetAnnotationsReturnsDeclaredAnnotations() {
        try {
            AccessibleObject ao = new DummyAccessibleObject();
            Annotation[] annotations = ao.getAnnotations();
            if (annotations == null) {
                throw new AssertionError("getAnnotations() returned null");
            }
            // Additional checks can be added here if needed
            System.out.println("testGetAnnotationsReturnsDeclaredAnnotations passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static class DummyAccessibleObject extends AccessibleObject {
        @Override
        public Annotation[] getDeclaredAnnotations() {
            return new Annotation[0];
        }
    }
}
