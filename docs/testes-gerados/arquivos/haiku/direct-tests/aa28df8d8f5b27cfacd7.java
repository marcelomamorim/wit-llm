/*
 * @test
 * @summary Tests the public EventObject(Object) constructor: null source rejection, source retention and toString
 * @run main EventObjectWitupTest
 */

public class EventObjectWitupTest {

    public static void main(String[] args) {
        testNullSourceRejected();
        testSourceIsRetained();
        testEmptyStringSourceAccepted();
        testToStringContainsClassNameAndSource();
    }

    static void testNullSourceRejected() {
        try {
            new java.util.EventObject(null);
        } catch (IllegalArgumentException e) {
            check("null source".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for null source");
    }

    static void testSourceIsRetained() {
        Object source = new Object();
        java.util.EventObject event = new java.util.EventObject(source);
        check(event.getSource() == source, "getSource() must return the same source instance");
    }

    static void testEmptyStringSourceAccepted() {
        java.util.EventObject event = new java.util.EventObject("");
        check("".equals(event.getSource()), "empty string source must be accepted and returned");
    }

    static void testToStringContainsClassNameAndSource() {
        java.util.EventObject event = new java.util.EventObject("abc");
        String expected = "java.util.EventObject[source=abc]";
        check(expected.equals(event.toString()),
              "unexpected toString: " + event.toString());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
