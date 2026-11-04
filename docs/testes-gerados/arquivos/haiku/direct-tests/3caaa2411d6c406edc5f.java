/*
 * @test
 * @summary Verify that AccessibleObject.getDeclaredAnnotations() fails with AssertionError when not overridden by a subclass
 * @run main AccessibleObjectWitupTest
 */

public class AccessibleObjectWitupTest {

    static class Probe extends java.lang.reflect.AccessibleObject {
        Probe() {
            super();
        }
    }

    public static void main(String[] args) {
        java.lang.reflect.AccessibleObject ao = new Probe();
        boolean thrown = false;
        try {
            ao.getDeclaredAnnotations();
        } catch (AssertionError e) {
            thrown = true;
            if (!"All subclasses should override this method".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected AssertionError was not thrown");
        }
    }
}
