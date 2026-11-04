/*
 * @test
 * @summary Test writeStartElement method for XMLStreamWriterImpl
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import jdk.internal.util.xml.impl.XMLStreamWriter;
import jdk.internal.util.xml.XMLStreamException;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteStartElementWithValidName();
        testWriteStartElementWithNullName();
        testWriteStartElementWithEmptyName();
    }

    static void testWriteStartElementWithValidName() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        XMLStreamWriter writer = new jdk.internal.util.xml.impl.XMLStreamWriterImpl(os);
        try {
            writer.writeStartElement("testElement");
            // No exception expected
        } catch (XMLStreamException e) {
            throw new AssertionError("Unexpected exception for valid name", e);
        }
    }

    static void testWriteStartElementWithNullName() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        XMLStreamWriter writer = new jdk.internal.util.xml.impl.XMLStreamWriterImpl(os);
        boolean thrown = false;
        try {
            writer.writeStartElement(null);
        } catch (XMLStreamException e) {
            thrown = true;
            if (!e.getMessage().contains("Local Name cannot be null or empty")) {
                throw new AssertionError("Incorrect exception message", e);
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected XMLStreamException for null localName");
        }
    }

    static void testWriteStartElementWithEmptyName() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        XMLStreamWriter writer = new jdk.internal.util.xml.impl.XMLStreamWriterImpl(os);
        boolean thrown = false;
        try {
            writer.writeStartElement("");
        } catch (XMLStreamException e) {
            thrown = true;
            if (!e.getMessage().contains("Local Name cannot be null or empty")) {
                throw new AssertionError("Incorrect exception message", e);
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected XMLStreamException for empty localName");
        }
    }
}
