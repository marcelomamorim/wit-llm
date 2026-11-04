/* @test    @summary Test for writeStartDocument with valid inputs and state checks    @run main XMLStreamWriterImplWitupTest */

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import jdk.internal.util.xml.impl.XMLStreamWriter;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;
import jdk.internal.util.xml.XMLStreamException;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteStartDocumentNormal();
        testWriteStartDocumentInvalidState();
    }

    static void testWriteStartDocumentNormal() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriterImpl(baos);
        // Set internal state to 0 to simulate initial state
        if (writer instanceof XMLStreamWriterImpl) {
            ((XMLStreamWriterImpl) writer)._state = 0;
        }
        // Call method with valid encoding and version
        writer.writeStartDocument("UTF-8", "1.0");
        // No exception expected, verify output if needed
        String output = baos.toString("UTF-8");
        // Optionally, check output contains expected start
        if (!output.contains("<?xml")) {
            throw new AssertionError("Output does not contain XML declaration");
        }
    }

    static void testWriteStartDocumentInvalidState() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriterImpl(baos);
        // Force internal state to > 0 to trigger exception path
        if (writer instanceof XMLStreamWriterImpl) {
            ((XMLStreamWriterImpl) writer)._state = 1;
        }
        boolean exceptionThrown = false;
        try {
            writer.writeStartDocument("UTF-8", "1.0");
        } catch (XMLStreamException e) {
            exceptionThrown = true;
            // Optionally, verify exception message
            if (!e.getMessage().contains("Invalid state")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected XMLStreamException was not thrown");
        }
    }
}
