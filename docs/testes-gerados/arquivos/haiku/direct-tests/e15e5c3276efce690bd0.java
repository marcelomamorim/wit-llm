/*
 * @test
 * @summary Verifies that the base AccessibleObject.getAnnotationsByType fails with AssertionError when not overridden
 * @run main AccessibleObjectWitupTest
 */

public class AccessibleObjectWitupTest {

    static class Probe extends java.lang.reflect.AccessibleObject {
    }

    public static void main(String[] args) {
        Probe p = new Probe();

        try {
            p.getAnnotationsByType(Override.class);
            throw new RuntimeException("Expected AssertionError was not thrown");
        } catch (AssertionError e) {
            if (!"All subclasses should override this method".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }

        try {
            p.getAnnotationsByType(null);
            throw new RuntimeException("Expected AssertionError was not thrown for null argument");
        } catch (AssertionError e) {
            if (!"All subclasses should override this method".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for null argument: " + e.getMessage());
            }
        }
    }
}
