/*
 * @test
 * @summary Test writeCData method of XMLStreamWriterImpl for normal and null input
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;
import jdk.internal.util.xml.XMLStreamException;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteCDataNormal();
        testWriteCDataNull();
    }

    static void testWriteCDataNormal() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos);
        String cdataContent = "Sample CDATA";
        writer.writeCData(cdataContent);
        String output = baos.toString("UTF-8");
        if (!output.contains(cdataContent)) {
            throw new AssertionError("CDATA content not found in output") ;
        }
        if (!output.contains("<![CDATA[")) {
            throw new AssertionError("Start CDATA marker missing") ;
        }
        if (!output.contains("]]>")) {
            throw new AssertionError("End CDATA marker missing") ;
        }
        System.out.println("testWriteCDataNormal passed");
    }

    static void testWriteCDataNull() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos);
            writer.writeCData(null);
            throw new AssertionError("Expected XMLStreamException for null cdata") ;
        } catch (XMLStreamException e) {
            if (!e.getMessage().contains("cdata cannot be null")) {
                throw new AssertionError("Unexpected exception message") ;
            }
            System.out.println("testWriteCDataNull passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type") ;
        }
    }
}
