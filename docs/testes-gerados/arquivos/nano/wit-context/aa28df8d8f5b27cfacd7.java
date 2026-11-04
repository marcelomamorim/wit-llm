/* @test    @summary Test for java.util.EventObject constructor handling null source    @run main EventObjectWitupTest */

import java.util.EventObject;

public class EventObjectWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullSource();
        testConstructorWithValidSource();
    }

    public static void testConstructorWithNullSource() {
        try {
            new EventObject(null);
            System.err.println("FAIL: Expected IllegalArgumentException for null source");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            if ("null source".equals(message)) {
                System.out.println("PASS: Constructor throws IllegalArgumentException with message 'null source'");
            } else {
                System.err.println("FAIL: Exception message mismatch: " + message);
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }

    public static void testConstructorWithValidSource() {
        Object sourceObj = new Object();
        EventObject event = new EventObject(sourceObj);
        if (event.getSource() == sourceObj) {
            System.out.println("PASS: Constructor correctly assigns source object");
        } else {
            System.err.println("FAIL: getSource() does not return the expected object");
        }
        String toStringOutput = event.toString();
        if (toStringOutput.contains("java.util.EventObject") && toStringOutput.contains("source=")) {
            System.out.println("PASS: toString() output contains class name and source info");
        } else {
            System.err.println("FAIL: toString() output does not contain expected info: " + toStringOutput);
        }
    }
}
