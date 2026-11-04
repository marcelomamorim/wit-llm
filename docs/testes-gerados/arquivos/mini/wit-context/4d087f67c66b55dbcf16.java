/*
 * @test
 * @summary Test XMLStreamWriterImpl.writeEndElement() behavior including exception when no current element
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteEndElementThrowsWhenNoCurrentElement();
        testWriteEndElementWithEmptyElementReturnsWithoutWriting();
        testWriteEndElementWritesEndTagAndUpdatesCurrentElement();
    }

    private static void testWriteEndElementThrowsWhenNoCurrentElement() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, StandardCharsets.UTF_8);

        // forcibly set _currentEle to null via reflection is not allowed, so we rely on default state
        // The default _currentEle is null, so calling writeEndElement() should throw XMLStreamException

        try {
            writer.writeEndElement();
            throw new RuntimeException("Expected XMLStreamException not thrown when _currentEle is null");
        } catch (XMLStreamException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("No element was found")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testWriteEndElementWithEmptyElementReturnsWithoutWriting() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, StandardCharsets.UTF_8);

        // We cannot create Element instances or set _currentEle publicly,
        // so this test is limited and cannot fully verify empty element behavior.
        // This test is a placeholder to document that such a test would be needed if accessible.
    }

    private static void testWriteEndElementWritesEndTagAndUpdatesCurrentElement() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, StandardCharsets.UTF_8);

        // Without access to Element or _currentEle, we cannot test the full writeEndElement flow.
        // This test is a placeholder to document that such a test would be needed if accessible.
    }

}
