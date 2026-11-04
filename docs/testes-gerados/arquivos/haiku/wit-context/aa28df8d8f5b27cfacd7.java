/*
 * @test
 * @summary Tests EventObject null-source rejection, source retrieval and toString using the public constructor
 * @run main EventObjectWitupTest
 */
public class EventObjectWitupTest {
    public static void main(String[] args) {
        testNullSourceRejected();
        testSourceIsPreserved();
        testToStringContainsSource();
    }

    static void testNullSourceRejected() {
        try {
            new java.util.EventObject(null);
            throw new RuntimeException("expected IllegalArgumentException for null source");
        } catch (IllegalArgumentException e) {
            if (!"null source".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testSourceIsPreserved() {
        Object src = new Object();
        java.util.EventObject ev = new java.util.EventObject(src);
        if (ev.getSource() != src) {
            throw new RuntimeException("getSource() did not return the constructor argument");
        }
    }

    static void testToStringContainsSource() {
        java.util.EventObject ev = new java.util.EventObject("abc");
        String expected = "java.util.EventObject[source=abc]";
        if (!expected.equals(ev.toString())) {
            throw new RuntimeException("unexpected toString: " + ev.toString());
        }
    }
}
