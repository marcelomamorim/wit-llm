/* @test    @summary Test for writeStartDocument(String version) method in XMLStreamWriterImpl
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import jdk.internal.util.xml.impl.XMLStreamWriter;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;
import jdk.internal.util.xml.XMLStreamException;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) {
        try {
            testWriteStartDocumentWithValidState();
            testWriteStartDocumentWithInvalidState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testWriteStartDocumentWithValidState() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriterImpl(baos);
        // Simulate valid state where _state == 0
        // As _state is private, assume initial state is 0
        writer.writeStartDocument("1.0");
        String output = baos.toString("UTF-8");
        // Verify that the method executes without exception and output is as expected
        if (output == null || output.isEmpty()) {
            throw new AssertionError("Output should not be empty after writeStartDocument");
        }
        System.out.println("testWriteStartDocumentWithValidState passed.");
    }

    private static void testWriteStartDocumentWithInvalidState() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriter writer = new XMLStreamWriterImpl(baos);
        // Reflection to set _state > 0 to simulate invalid state
        java.lang.reflect.Field stateField = XMLStreamWriterImpl.class.getDeclaredField("_state");
        stateField.setAccessible(true);
        stateField.setInt(writer, 1); // Set state to > 0
        try {
            writer.writeStartDocument("1.0");
            throw new AssertionError("Expected XMLStreamException was not thrown");
        } catch (XMLStreamException e) {
            // Expected exception
            System.out.println("testWriteStartDocumentWithInvalidState passed.");
        }
    }
}
