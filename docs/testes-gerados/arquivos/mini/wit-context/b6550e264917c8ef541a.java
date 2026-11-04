/*
 * @test
 * @summary Test AccessibleObject.getAnnotation method throws AssertionError as expected
 * @run main java.lang.reflect.AccessibleObjectWitupTest
 */

public class AccessibleObjectWitupTest {
    public static void main(String[] args) {
        AccessibleObject ao = new AccessibleObject() {};
        try {
            ao.getAnnotation(Deprecated.class);
            throw new RuntimeException("Expected AssertionError not thrown");
        } catch (AssertionError e) {
            // Expected exception
        }
    }
}
