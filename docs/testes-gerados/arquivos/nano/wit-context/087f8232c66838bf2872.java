/*
 * @test
 * @summary Tests for jdk.internal.util.xml.impl.XMLStreamWriterImpl
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import jdk.internal.util.xml.impl.XMLStreamWriter;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;
import jdk.internal.util.xml.XMLStreamException;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) {
        testWriteStartElementWithNull();
        testWriteStartElementWithEmpty();
        testWriteStartElementWithValidName();
    }

    static void testWriteStartElementWithNull() {
        OutputStream os = new ByteArrayOutputStream();
        try {
            XMLStreamWriter writer = new XMLStreamWriterImpl(os);
            writer.writeStartElement(null);
            System.out.println("FAIL: Expected XMLStreamException for null localName");
        } catch (XMLStreamException e) {
            System.out.println("PASS: Caught expected XMLStreamException for null localName");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    static void testWriteStartElementWithEmpty() {
        OutputStream os = new ByteArrayOutputStream();
        try {
            XMLStreamWriter writer = new XMLStreamWriterImpl(os);
            writer.writeStartElement("");
            System.out.println("FAIL: Expected XMLStreamException for empty localName");
        } catch (XMLStreamException e) {
            System.out.println("PASS: Caught expected XMLStreamException for empty localName");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    static void testWriteStartElementWithValidName() {
        OutputStream os = new ByteArrayOutputStream();
        try {
            XMLStreamWriter writer = new XMLStreamWriterImpl(os);
            writer.writeStartElement("testElement");
            System.out.println("PASS: writeStartElement with valid name succeeded");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
