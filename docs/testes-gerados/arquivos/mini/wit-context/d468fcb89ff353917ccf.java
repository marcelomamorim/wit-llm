/*
 * @test
 * @summary Test XMLStreamWriterImpl.writeStartDocument(String) for state and exception behavior
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteStartDocumentNormal();
        testWriteStartDocumentThrowsWhenStatePositive();
    }

    private static void testWriteStartDocumentNormal() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, Charset.forName("UTF-8"));
        // Initially _state is 0, so no exception expected
        writer.writeStartDocument("1.0");
        // No exception means test passed
    }

    private static void testWriteStartDocumentThrowsWhenStatePositive() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, Charset.forName("UTF-8"));

        // Use reflection to set _state to a positive value to trigger exception path
        java.lang.reflect.Field stateField = XMLStreamWriterImpl.class.getDeclaredField("_state");
        stateField.setAccessible(true);
        stateField.setInt(writer, 1); // _state > 0

        boolean thrown = false;
        try {
            writer.writeStartDocument("1.0");
        } catch (XMLStreamException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected XMLStreamException when _state > 0");
        }
    }
}
