/*
 * @test
 * @summary Test AccessibleObject.getAnnotationsByType throws AssertionError by default
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {
    public static void main(String[] args) {
        AccessibleObject ao = new AccessibleObject() {};
        try {
            ao.getAnnotationsByType(Deprecated.class);
            throw new RuntimeException("Expected AssertionError not thrown");
        } catch (AssertionError e) {
            // Expected exception
        }
    }
}
