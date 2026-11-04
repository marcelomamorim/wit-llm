/*
 * @test
 * @summary Test sun.net.www.MessageHeader.remove() throws UnsupportedOperationException
 * @run main MessageHeaderWitupTest
 */

import sun.net.www.MessageHeader;

public class MessageHeaderWitupTest {
    public static void main(String[] args) {
        MessageHeader mh = new MessageHeader();
        try {
            mh.remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
