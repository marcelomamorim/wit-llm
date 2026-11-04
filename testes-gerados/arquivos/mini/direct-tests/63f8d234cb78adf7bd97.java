/*
 * @test
 * @summary Test XMLStreamWriterImpl.writeCData behavior including null input and normal usage
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteCDataNull();
        testWriteCDataNormal();
    }

    private static void testWriteCDataNull() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, StandardCharsets.UTF_8);
            writer.writeCData(null);
            throw new RuntimeException("Expected XMLStreamException for null cdata");
        } catch (XMLStreamException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("cdata cannot be null")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testWriteCDataNormal() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos, StandardCharsets.UTF_8);

        // Write a simple CDATA section
        String cdata = "some <xml> & data";
        writer.writeCData(cdata);

        // The output should contain <![CDATA[some <xml> & data]]>
        String output = baos.toString(StandardCharsets.UTF_8.name());
        if (!output.contains("<![CDATA[")) {
            throw new RuntimeException("Output missing CDATA start tag");
        }
        if (!output.contains(cdata)) {
            throw new RuntimeException("Output missing CDATA content");
        }
        if (!output.contains("]]>")) {
            throw new RuntimeException("Output missing CDATA end tag");
        }
    }
}
