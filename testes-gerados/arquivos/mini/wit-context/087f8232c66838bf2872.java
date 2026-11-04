/*
 * @test
 * @summary Test XMLStreamWriterImpl.writeStartElement(String) for valid and invalid inputs
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;
import jdk.internal.util.xml.XMLStreamException;

public class XMLStreamWriterImplWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteStartElementValid();
        testWriteStartElementNull();
        testWriteStartElementEmpty();
    }

    private static void testWriteStartElementValid() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, StandardCharsets.UTF_8);

        // Write a valid start element
        writer.writeStartElement("testElement");

        // The internal state should be STATE_ELEMENT (4) after call
        // We cannot access _state directly, so we test observable behavior:
        // The output stream should contain the element name written
        String output = baos.toString("UTF-8");
        if (!output.contains("testElement")) {
            throw new AssertionError("Output does not contain the element name");
        }
    }

    private static void testWriteStartElementNull() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, StandardCharsets.UTF_8);

        try {
            writer.writeStartElement(null);
            throw new AssertionError("Expected XMLStreamException for null localName");
        } catch (XMLStreamException e) {
            // Expected exception
        }
    }

    private static void testWriteStartElementEmpty() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, StandardCharsets.UTF_8);

        try {
            writer.writeStartElement("");
            throw new AssertionError("Expected XMLStreamException for empty localName");
        } catch (XMLStreamException e) {
            // Expected exception
        }
    }
}
