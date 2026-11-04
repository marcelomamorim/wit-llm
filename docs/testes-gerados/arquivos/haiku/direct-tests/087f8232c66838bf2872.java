/*
 * @test
 * @summary Verifies writeStartElement rejects null or empty local names and accepts a valid name
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
        testNullLocalNameThrows();
        testEmptyLocalNameThrows();
        testValidLocalNameDoesNotThrow();
        testValidLocalNameWithCharsetDoesNotThrow();
    }

    static void testNullLocalNameThrows() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XMLStreamWriterImpl w = new XMLStreamWriterImpl(os);
        boolean thrown = false;
        try {
            w.writeStartElement(null);
        } catch (XMLStreamException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected XMLStreamException for null local name");
        }
    }

    static void testEmptyLocalNameThrows() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XMLStreamWriterImpl w = new XMLStreamWriterImpl(os);
        boolean thrown = false;
        try {
            w.writeStartElement("");
        } catch (XMLStreamException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected XMLStreamException for empty local name");
        }
    }

    static void testValidLocalNameDoesNotThrow() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XMLStreamWriterImpl w = new XMLStreamWriterImpl(os);
        w.writeStartElement("root");
    }

    static void testValidLocalNameWithCharsetDoesNotThrow() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XMLStreamWriterImpl w = new XMLStreamWriterImpl(os, StandardCharsets.UTF_8);
        w.writeStartElement("item");
    }
}
