/* @test    @summary Test writeStartDocument with version parameter in XMLStreamWriterImpl    @run main XMLStreamWriterImplWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import jdk.internal.util.xml.impl.XMLStreamWriter;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;
import jdk.internal.util.xml.XMLStreamException;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteStartDocument();
    }

    public static void testWriteStartDocument() throws XMLStreamException {
        OutputStream os = new ByteArrayOutputStream();
        Charset charset = Charset.forName("UTF-8");
        XMLStreamWriter writer = new XMLStreamWriterImpl(os, charset);
        String version = "1.0";
        writer.writeStartDocument(version);
        // Verifica se o método foi executado sem lançar exceções e se o objeto não é nulo
        if (writer == null) {
            throw new AssertionError("Writer is null");
        }
    }
}
