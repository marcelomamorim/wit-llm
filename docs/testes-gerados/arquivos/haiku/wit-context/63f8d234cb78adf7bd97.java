/*
 * @test
 * @summary Verify writeCData rejects null input with XMLStreamException and accepts a non-null value afterwards
 * @modules java.base/jdk.internal.util.xml java.base/jdk.internal.util.xml.impl
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(bos);

        boolean thrown = false;
        try {
            writer.writeCData(null);
        } catch (XMLStreamException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("writeCData(null) did not throw XMLStreamException");
        }

        writer.writeCData("data");
    }
}
