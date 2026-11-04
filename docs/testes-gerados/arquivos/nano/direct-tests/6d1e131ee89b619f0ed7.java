/* @test    @summary Test getAnnotations method of AccessibleObject for correct behavior    @run main AccessibleObjectWitupTest */

import java.lang.reflect.AccessibleObject;
import java.lang.annotation.Annotation;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        testGetAnnotationsReturnsDeclaredAnnotations();
    }

    private static void testGetAnnotationsReturnsDeclaredAnnotations() {
        class TestClass {
            @Deprecated
            public void testMethod() {}
        }
        try {
            AccessibleObject ao = TestClass.class.getMethod("testMethod");
            Annotation[] annotations = ao.getAnnotations();
            boolean hasDeprecated = false;
            for (Annotation a : annotations) {
                if (a.annotationType() == Deprecated.class) {
                    hasDeprecated = true;
                    break;
                }
            }
            if (!hasDeprecated) {
                throw new AssertionError("Expected Deprecated annotation in getAnnotations() result.");
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
