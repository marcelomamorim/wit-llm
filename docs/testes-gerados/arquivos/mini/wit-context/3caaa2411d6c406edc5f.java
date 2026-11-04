/*
 * @test
 * @summary Test AccessibleObject.getDeclaredAnnotations throws AssertionError as expected
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        AccessibleObject ao = new AccessibleObject() { };
        try {
            ao.getDeclaredAnnotations();
            throw new RuntimeException("Expected AssertionError not thrown");
        } catch (AssertionError e) {
            // Expected exception, test passes
        }
    }
}
