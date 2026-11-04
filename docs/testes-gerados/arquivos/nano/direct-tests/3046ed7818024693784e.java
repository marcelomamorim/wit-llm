/* @test    @summary Test MessageHeader.remove() method for UnsupportedOperationException    @run main MessageHeaderWitupTest */

import java.io.*;

public class MessageHeaderWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        sun.net.www.MessageHeader header = new sun.net.www.MessageHeader();
        try {
            header.remove();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
