/*
 * @test
 * @summary Checks that writeStartDocument(String, String) throws XMLStreamException when the document state is already past the start
 * @modules java.base/jdk.internal.util.xml
 *          java.base/jdk.internal.util.xml.impl
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        firstStartDocumentDoesNotThrow();
        secondStartDocumentThrows();
    }

    static void firstStartDocumentDoesNotThrow() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(bos);
        try {
            writer.writeStartDocument("UTF-8", "1.0");
        } catch (XMLStreamException e) {
            throw new RuntimeException("First writeStartDocument must not throw", e);
        }
    }

    static void secondStartDocumentThrows() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(bos);
        writer.writeStartDocument("UTF-8", "1.0");
        boolean thrown = false;
        try {
            writer.writeStartDocument("UTF-8", "1.0");
        } catch (XMLStreamException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Second writeStartDocument must throw XMLStreamException");
        }
    }
}
