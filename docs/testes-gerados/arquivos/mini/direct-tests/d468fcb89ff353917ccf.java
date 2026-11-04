/*
 * @test
 * @summary Test XMLStreamWriterImpl.writeStartDocument(String) with valid and default charset
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteStartDocumentWithValidVersion();
        testWriteStartDocumentWithNullVersion();
    }

    private static void testWriteStartDocumentWithValidVersion() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, Charset.forName("UTF-8"));
        String version = "1.0";
        writer.writeStartDocument(version);
        // The output should start with XML declaration containing version and encoding
        String output = baos.toString("UTF-8");
        if (!output.contains("version=\"" + version + "\"")) {
            throw new AssertionError("Output does not contain expected version attribute");
        }
        if (!output.toLowerCase().contains("encoding=")) {
            throw new AssertionError("Output does not contain encoding attribute");
        }
    }

    private static void testWriteStartDocumentWithNullVersion() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, Charset.forName("UTF-8"));
        // Passing null version should not throw and should produce XML declaration with default version
        writer.writeStartDocument(null);
        String output = baos.toString("UTF-8");
        if (!output.toLowerCase().contains("version=")) {
            throw new AssertionError("Output does not contain version attribute when version is null");
        }
    }
}
