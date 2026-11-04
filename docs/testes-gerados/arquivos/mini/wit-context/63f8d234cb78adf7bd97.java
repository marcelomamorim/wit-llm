/*
 * @test
 * @summary Test XMLStreamWriterImpl.writeCData behavior including null input exception
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteCDataWithValidString();
        testWriteCDataWithNullThrows();
    }

    private static void testWriteCDataWithValidString() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, StandardCharsets.UTF_8);
        String cdata = "some cdata content";
        writer.writeCData(cdata);
        writer.flush();
        String output = baos.toString(StandardCharsets.UTF_8.name());
        if (!output.contains("<![CDATA[" + cdata + "]]>")) {
            throw new AssertionError("Output does not contain expected CDATA section: " + output);
        }
    }

    private static void testWriteCDataWithNullThrows() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, StandardCharsets.UTF_8);
        try {
            writer.writeCData(null);
            throw new AssertionError("Expected XMLStreamException for null cdata input");
        } catch (XMLStreamException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("cdata cannot be null")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}
