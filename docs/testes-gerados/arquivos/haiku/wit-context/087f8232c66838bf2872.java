/*
 * @test
 * @summary Witup-generated checks for XMLStreamWriterImpl.writeStartElement argument validation
 * @modules java.base/jdk.internal.util.xml java.base/jdk.internal.util.xml.impl
 * @run main XMLStreamWriterImplWitupTest
 */
import java.io.ByteArrayOutputStream;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {
    public static void main(String[] args) throws Exception {
        testNullLocalName();
        testEmptyLocalName();
        testValidLocalName();
    }

    static void testNullLocalName() throws Exception {
        XMLStreamWriterImpl w = new XMLStreamWriterImpl(new ByteArrayOutputStream());
        try {
            w.writeStartElement(null);
            throw new AssertionError("expected XMLStreamException for null localName");
        } catch (XMLStreamException e) {
            // expected
        }
    }

    static void testEmptyLocalName() throws Exception {
        XMLStreamWriterImpl w = new XMLStreamWriterImpl(new ByteArrayOutputStream());
        try {
            w.writeStartElement("");
            throw new AssertionError("expected XMLStreamException for empty localName");
        } catch (XMLStreamException e) {
            // expected
        }
    }

    static void testValidLocalName() throws Exception {
        XMLStreamWriterImpl w = new XMLStreamWriterImpl(new ByteArrayOutputStream());
        w.writeStartElement("root");
    }
}
