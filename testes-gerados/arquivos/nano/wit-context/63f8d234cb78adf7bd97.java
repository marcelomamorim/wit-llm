/*
 * @test
 * @summary Test for writeCData method in XMLStreamWriterImpl
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
        testWriteCDataWithNull();
        testWriteCDataWithValidString();
    }

    private static void testWriteCDataWithNull() {
        OutputStream os = new ByteArrayOutputStream();
        try {
            XMLStreamWriter writer = new XMLStreamWriterImpl(os);
            boolean exceptionThrown = false;
            try {
                writer.writeCData(null);
            } catch (XMLStreamException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected XMLStreamException for null cdata");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testWriteCDataWithValidString() {
        OutputStream os = new ByteArrayOutputStream();
        try {
            XMLStreamWriter writer = new XMLStreamWriterImpl(os);
            String cdataContent = "Sample CDATA content";
            writer.writeCData(cdataContent);
            String output = os.toString("UTF-8");
            String expectedStart = "<![CDATA[";
            String expectedEnd = "]]>";
            if (!output.contains(expectedStart) || !output.contains(expectedEnd)) {
                throw new AssertionError("Output does not contain expected CDATA delimiters: " + output);
            }
            if (!output.contains(cdataContent)) {
                throw new AssertionError("CDATA content not found in output: " + output);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
