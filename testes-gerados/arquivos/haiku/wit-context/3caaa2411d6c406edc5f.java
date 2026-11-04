/*
 * @test
 * @summary Verify that the base AccessibleObject.getDeclaredAnnotations() throws AssertionError when a subclass does not override it
 * @run main AccessibleObjectWitupTest
 */

import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    static class Dummy extends AccessibleObject {
    }

    public static void main(String[] args) {
        AccessibleObject obj = new Dummy();
        try {
            obj.getDeclaredAnnotations();
            throw new RuntimeException("expected AssertionError was not thrown");
        } catch (AssertionError e) {
            if (!"All subclasses should override this method".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }
}
