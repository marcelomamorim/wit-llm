/*
 * @test
 * @summary Test constructor of java.util.EventObject for valid and null source
 * @run main EventObjectWitupTest
 */

public class EventObjectWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidSource();
        testConstructorWithNullSource();
    }

    private static void testConstructorWithValidSource() {
        Object src = new Object();
        java.util.EventObject event = new java.util.EventObject(src);
        if (event.getSource() != src) {
            throw new AssertionError("getSource() did not return the source passed to constructor");
        }
        String str = event.toString();
        if (!str.contains(src.toString())) {
            throw new AssertionError("toString() does not contain source string representation");
        }
    }

    private static void testConstructorWithNullSource() {
        try {
            new java.util.EventObject(null);
            throw new AssertionError("Constructor did not throw IllegalArgumentException for null source");
        } catch (IllegalArgumentException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.equals("null source")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}
