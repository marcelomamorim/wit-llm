/*
 * @test
 * @summary Verifies that the base AccessibleObject.getAnnotation throws AssertionError for any argument
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        AccessibleObject ao = new AccessibleObject() {};
        expectAssertionError(ao, Deprecated.class);
        expectAssertionError(ao, Override.class);
        Class<? extends Annotation> nullClass = null;
        expectAssertionError(ao, nullClass);
    }

    private static void expectAssertionError(AccessibleObject ao, Class<? extends Annotation> c) {
        try {
            ao.getAnnotation(c);
        } catch (AssertionError e) {
            return;
        }
        throw new RuntimeException("Expected AssertionError from AccessibleObject.getAnnotation");
    }
}
