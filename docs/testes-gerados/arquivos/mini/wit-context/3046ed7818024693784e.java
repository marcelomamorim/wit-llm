/*
 * @test
 * @summary Test sun.net.www.MessageHeader.remove() throws UnsupportedOperationException
 * @run main MessageHeaderWitupTest
 */

public class MessageHeaderWitupTest {
    public static void main(String[] args) {
        sun.net.www.MessageHeader header = new sun.net.www.MessageHeader();
        try {
            header.remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
