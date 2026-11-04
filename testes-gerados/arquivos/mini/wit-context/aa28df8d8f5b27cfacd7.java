/*
 * @test
 * @summary Test constructor of java.util.EventObject for null source and valid source
 * @run main EventObjectWitupTest
 */

public class EventObjectWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidSource();
        testConstructorWithNullSource();
    }

    private static void testConstructorWithValidSource() {
        Object source = new Object();
        java.util.EventObject event = new java.util.EventObject(source);
        if (event.getSource() != source) {
            throw new AssertionError("getSource() did not return the source passed to constructor");
        }
        String toString = event.toString();
        if (toString == null || !toString.contains(source.toString())) {
            throw new AssertionError("toString() does not contain source's toString representation");
        }
    }

    private static void testConstructorWithNullSource() {
        try {
            new java.util.EventObject(null);
            throw new AssertionError("Constructor did not throw IllegalArgumentException when source is null");
        } catch (IllegalArgumentException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("null source")) {
                throw new AssertionError("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }
}
