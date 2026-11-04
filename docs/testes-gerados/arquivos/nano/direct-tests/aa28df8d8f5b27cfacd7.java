/* @test    @summary Test EventObject constructor with valid source object    @run main EventObjectWitupTest */

import java.util.EventObject;

public class EventObjectWitupTest {

    public static void main(String[] args) {
        testValidSourceObject();
        testNullSourceThrowsException();
    }

    private static void testValidSourceObject() {
        Object source = new Object();
        EventObject event = new EventObject(source);
        assert event.getSource() == source : "Source object should be the same as the one passed";
        String str = event.toString();
        assert str.contains("java.util.EventObject") : "toString should contain class name";
        assert str.contains("source=") : "toString should contain source";
    }

    private static void testNullSourceThrowsException() {
        boolean exceptionThrown = false;
        try {
            new EventObject(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            assert e.getMessage().equals("null source") : "Exception message should be 'null source'";
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException when source is null";
    }
}
