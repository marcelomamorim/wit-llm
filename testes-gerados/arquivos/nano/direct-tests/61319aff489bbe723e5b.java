/* @test    @summary Test writeStartDocument with encoding and version parameters    @run main XMLStreamWriterImplWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import jdk.internal.util.xml.impl.XMLStreamWriter;
import jdk.internal.util.xml.XMLStreamException;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteStartDocument();
    }

    public static void testWriteStartDocument() throws XMLStreamException {
        OutputStream os = new ByteArrayOutputStream();
        XMLStreamWriter writer = new jdk.internal.util.xml.impl.XMLStreamWriterImpl(os);
        String encoding = "UTF-8";
        String version = "1.0";
        writer.writeStartDocument(encoding, version);
        // No exception thrown indicates success
        System.out.println("writeStartDocument executed successfully.");
    }
}
