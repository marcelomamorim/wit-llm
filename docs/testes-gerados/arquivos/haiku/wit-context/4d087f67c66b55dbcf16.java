/*
 * @test
 * @summary XMLStreamWriterImpl.writeEndElement throws XMLStreamException when no element is open
 * @modules java.base/jdk.internal.util.xml java.base/jdk.internal.util.xml.impl
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteEndElementWithoutOpenElementThrows();
        testWriteEndElementWithCharsetConstructorThrows();
        testRepeatedWriteEndElementStillThrows();
    }

    static void testWriteEndElementWithoutOpenElementThrows() throws Exception {
        XMLStreamWriterImpl writer =
                new XMLStreamWriterImpl(new ByteArrayOutputStream());
        expectXMLStreamException(writer);
    }

    static void testWriteEndElementWithCharsetConstructorThrows() throws Exception {
        XMLStreamWriterImpl writer =
                new XMLStreamWriterImpl(new ByteArrayOutputStream(),
                                        StandardCharsets.UTF_8);
        expectXMLStreamException(writer);
    }

    static void testRepeatedWriteEndElementStillThrows() throws Exception {
        XMLStreamWriterImpl writer =
                new XMLStreamWriterImpl(new ByteArrayOutputStream());
        expectXMLStreamException(writer);
        expectXMLStreamException(writer);
    }

    static void expectXMLStreamException(XMLStreamWriterImpl writer) {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException e) {
            return;
        }
        throw new RuntimeException("Expected XMLStreamException was not thrown");
    }
}
