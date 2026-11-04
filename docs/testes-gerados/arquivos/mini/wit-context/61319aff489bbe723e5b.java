/*
 * @test
 * @summary Test writeStartDocument(String encoding, String version) method of XMLStreamWriterImpl
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteStartDocumentValid();
        testWriteStartDocumentStateException();
    }

    private static void testWriteStartDocumentValid() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, Charset.forName("UTF-8"));
        // Initially _state is 0, so no exception expected
        writer.writeStartDocument("UTF-8", "1.0");
        // No exception means test passed for normal call
    }

    private static void testWriteStartDocumentStateException() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, Charset.forName("UTF-8"));

        // Use reflection to set _state > 0 to trigger exception path
        // _state is private, no public setter, so reflection is needed
        // Reflection justified because no public API to set state and to test exception path
        java.lang.reflect.Field stateField = XMLStreamWriterImpl.class.getDeclaredField("_state");
        stateField.setAccessible(true);
        stateField.setInt(writer, 1); // set _state > 0

        boolean thrown = false;
        try {
            writer.writeStartDocument("UTF-8", "1.0");
        } catch (XMLStreamException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected XMLStreamException when _state > 0");
        }
    }
}
