/*
 * @test
 * @summary Verify that XMLStreamWriterImpl.writeStartDocument(String version) writes the requested version in the XML declaration
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testDeclarationContainsRequestedVersion();
        testDifferentVersionsProduceDifferentDeclarations();
        testDeclarationStartsWithXmlDecl();
    }

    static String write(String version) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(bos);
        writer.writeStartDocument(version);
        return bos.toString("UTF-8");
    }

    static void testDeclarationContainsRequestedVersion() throws Exception {
        String out = write("1.0");
        if (!out.contains("version=\"1.0\"")) {
            throw new RuntimeException("Version 1.0 not found in output: " + out);
        }
    }

    static void testDifferentVersionsProduceDifferentDeclarations() throws Exception {
        String out10 = write("1.0");
        String out11 = write("1.1");
        if (!out11.contains("version=\"1.1\"")) {
            throw new RuntimeException("Version 1.1 not found in output: " + out11);
        }
        if (out10.equals(out11)) {
            throw new RuntimeException("Different versions produced identical output");
        }
    }

    static void testDeclarationStartsWithXmlDecl() throws Exception {
        String out = write("1.0");
        if (!out.startsWith("<?xml")) {
            throw new RuntimeException("Output does not start with XML declaration: " + out);
        }
    }
}
