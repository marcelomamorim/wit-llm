/*
 * @test
 * @summary Test XMLStreamWriterImpl.writeStartElement(String) behavior
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteStartElementValid();
        testWriteStartElementNull();
        testWriteStartElementEmpty();
    }

    private static void testWriteStartElementValid() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, Charset.forName("UTF-8"));

        // Write first element
        writer.writeStartElement("root");
        // Write nested element
        writer.writeStartElement("child");

        // The output stream should contain the last written localName "child" at least
        String output = baos.toString("UTF-8");
        if (!output.contains("child")) {
            throw new AssertionError("Output does not contain expected element name 'child': " + output);
        }
    }

    private static void testWriteStartElementNull() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, Charset.forName("UTF-8"));

        try {
            writer.writeStartElement(null);
            throw new AssertionError("Expected XMLStreamException for null localName");
        } catch (XMLStreamException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Local Name cannot be null or empty")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testWriteStartElementEmpty() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, Charset.forName("UTF-8"));

        try {
            writer.writeStartElement("");
            throw new AssertionError("Expected XMLStreamException for empty localName");
        } catch (XMLStreamException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Local Name cannot be null or empty")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
