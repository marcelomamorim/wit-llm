/*
 * @test
 * @summary Witup generated checks for XMLStreamWriterImpl.writeStartDocument(String)
 * @modules java.base/jdk.internal.util.xml
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLStreamWriterImpl w = new XMLStreamWriterImpl(bos);
        w.writeStartDocument("1.0");
        boolean thrown = false;
        try {
            w.writeStartDocument("1.0");
        } catch (XMLStreamException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("second writeStartDocument call should throw XMLStreamException");
        }
    }
}
