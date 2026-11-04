/*
 * @test
 * @summary Verifies observable XML declaration output of XMLStreamWriterImpl.writeStartDocument(String, String)
 * @modules java.base/jdk.internal.util.xml
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testValidVersionAppearsInOutput();
        testOtherVersionAppearsInOutput();
    }

    static String writeDeclaration(String encoding, String version) throws XMLStreamException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(bos);
        writer.writeStartDocument(encoding, version);
        writer.flush();
        return new String(bos.toByteArray(), StandardCharsets.UTF_8);
    }

    static void testValidVersionAppearsInOutput() throws Exception {
        String out = writeDeclaration("UTF-8", "1.0");
        if (!out.contains("<?xml")) {
            throw new RuntimeException("Missing XML declaration start: " + out);
        }
        if (!out.contains("version=\"1.0\"")) {
            throw new RuntimeException("Missing version 1.0: " + out);
        }
    }

    static void testOtherVersionAppearsInOutput() throws Exception {
        String out = writeDeclaration("UTF-8", "1.1");
        if (!out.contains("version=\"1.1\"")) {
            throw new RuntimeException("Missing version 1.1: " + out);
        }
        if (out.contains("version=\"1.0\"")) {
            throw new RuntimeException("Unexpected version 1.0: " + out);
        }
    }
}
