/*
 * @test
 * @summary Verify that writeEndElement throws XMLStreamException when no element is open
 * @modules java.base/jdk.internal.util.xml.impl java.base/jdk.internal.util.xml
 * @run main XMLStreamWriterImplWitupTest
 */
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNoElementThrows(new XMLStreamWriterImpl(new ByteArrayOutputStream()));
        testNoElementThrows(new XMLStreamWriterImpl(new ByteArrayOutputStream(), StandardCharsets.UTF_8));
        testNoElementThrows(new XMLStreamWriterImpl(new ByteArrayOutputStream(), null));
    }

    static void testNoElementThrows(XMLStreamWriterImpl writer) {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException e) {
            if (!"No element was found to write".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("expected XMLStreamException was not thrown");
    }
}
