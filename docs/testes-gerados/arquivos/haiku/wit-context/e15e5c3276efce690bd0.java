/*
 * @test
 * @summary Verify that AccessibleObject.getAnnotationsByType throws AssertionError when not overridden by a subclass
 * @run main AccessibleObjectWitupTest
 */
public class AccessibleObjectWitupTest {

    static class Probe extends java.lang.reflect.AccessibleObject {
        Probe() {
            super();
        }
    }

    public static void main(String[] args) {
        Probe p = new Probe();
        expectAssertionError(p, Deprecated.class);
        expectAssertionError(p, null);
    }

    static void expectAssertionError(Probe p, Class<Deprecated> c) {
        try {
            p.getAnnotationsByType(c);
            throw new RuntimeException("Expected AssertionError was not thrown");
        } catch (AssertionError e) {
            if (!"All subclasses should override this method".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
