/*
 * @test
 * @summary Test writeStartDocument(String encoding, String version) method of XMLStreamWriterImpl
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteStartDocumentWithNullEncodingAndVersion();
        testWriteStartDocumentWithValidEncodingAndVersion();
    }

    private static void testWriteStartDocumentWithNullEncodingAndVersion() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos);
        // encoding and version null, should not throw and writeStartDocument calls writeStartDocument(encoding, version, null)
        writer.writeStartDocument(null, null);
        // No exception means pass
    }

    private static void testWriteStartDocumentWithValidEncodingAndVersion() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, Charset.forName("UTF-8"));
        // valid encoding and version
        writer.writeStartDocument("UTF-8", "1.0");
        // No exception means pass
    }
}
