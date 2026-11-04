/*
 * @test
 * @summary MessageHeader.remove() must always throw UnsupportedOperationException and leave the header unchanged
 * @modules java.base/sun.net.www
 * @run main MessageHeaderWitupTest
 */

import sun.net.www.MessageHeader;
import java.io.ByteArrayInputStream;

public class MessageHeaderWitupTest {
    public static void main(String[] args) throws Exception {
        MessageHeader empty = new MessageHeader();
        expectUnsupported(empty);

        MessageHeader parsed = new MessageHeader(
                new ByteArrayInputStream("A: b\n\n".getBytes("US-ASCII")));
        expectUnsupported(parsed);

        if (!"A".equals(parsed.getKey(0))) {
            throw new RuntimeException("Header key changed after remove(): " + parsed.getKey(0));
        }
        if (!"b".equals(parsed.getValue(0))) {
            throw new RuntimeException("Header value changed after remove(): " + parsed.getValue(0));
        }
    }

    static void expectUnsupported(MessageHeader h) {
        try {
            h.remove();
        } catch (UnsupportedOperationException e) {
            if (!"remove not allowed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("remove() did not throw UnsupportedOperationException");
    }
}
