/*
 * @test
 * @summary Verify that the iterator returned by MessageHeader.multiValueIterator rejects remove() with UnsupportedOperationException
 * @modules java.base/sun.net.www
 * @run main MessageHeaderWitupTest
 */
import sun.net.www.MessageHeader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

public class MessageHeaderWitupTest {
    public static void main(String[] args) throws IOException {
        MessageHeader empty = new MessageHeader();
        Iterator<String> emptyIt = empty.multiValueIterator("Foo");
        expectUnsupported(emptyIt);

        MessageHeader parsed = new MessageHeader(
                new ByteArrayInputStream("Foo: bar\n\n".getBytes("US-ASCII")));
        Iterator<String> it = parsed.multiValueIterator("Foo");
        if (it.hasNext()) {
            it.next();
        }
        expectUnsupported(it);
    }

    private static void expectUnsupported(Iterator<String> it) {
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("remove() did not throw UnsupportedOperationException");
    }
}
