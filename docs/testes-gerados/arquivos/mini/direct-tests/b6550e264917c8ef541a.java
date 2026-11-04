/*
 * @test
 * @summary Test AccessibleObject.getAnnotation throws AssertionError as per base implementation
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {
    public static void main(String[] args) {
        AccessibleObject ao = new AccessibleObject() {};
        try {
            ao.getAnnotation(Deprecated.class);
            throw new RuntimeException("Expected AssertionError not thrown");
        } catch (AssertionError e) {
            // Expected path: base method throws AssertionError
        }
    }
}
