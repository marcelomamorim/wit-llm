/* @test    @summary Test for MessageHeader.remove() method to verify it throws UnsupportedOperationException.    @run main MessageHeaderWitupTest */

import sun.net.www.MessageHeader;

public class MessageHeaderWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        MessageHeader header = new MessageHeader();
        try {
            header.remove();
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.err.println("Failed: Caught unexpected exception: " + t);
        }
    }
}
