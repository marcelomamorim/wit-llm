/* @test    @summary Test getDeclaredAnnotations method in AccessibleObject for exception path    @run main AccessibleObjectWitupTest */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    public static void main(String[] args) {
        testGetDeclaredAnnotationsThrowsAssertionError();
    }

    private static void testGetDeclaredAnnotationsThrowsAssertionError() {
        AccessibleObject ao = new AccessibleObject() {
            @Override
            public Annotation[] getDeclaredAnnotations() {
                throw new AssertionError("All subclasses should override this method");
            }
        };
        boolean threwAssertion = false;
        try {
            ao.getDeclaredAnnotations();
        } catch (AssertionError e) {
            threwAssertion = true;
        }
        if (!threwAssertion) {
            throw new AssertionError("Expected AssertionError was not thrown");
        }
    }
}
