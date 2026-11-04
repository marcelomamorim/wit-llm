/*
 * @test
 * @summary Verifies XMLStreamWriterImpl.writeCData null-argument rejection and normal invocation
 * @modules java.base/jdk.internal.util.xml.impl java.base/jdk.internal.util.xml
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import jdk.internal.util.xml.XMLStreamException;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCDataThrows();
        testNonNullCDataDoesNotThrow();
        testEmptyCDataDoesNotThrow();
    }

    static void testNullCDataThrows() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(bos);
        try {
            writer.writeCData(null);
            throw new AssertionError("Expected XMLStreamException for null cdata");
        } catch (XMLStreamException e) {
            if (!"cdata cannot be null".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testNonNullCDataDoesNotThrow() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(bos);
        writer.writeCData("hello");
    }

    static void testEmptyCDataDoesNotThrow() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLStreamWriterImpl writer = new XMLStreamWriterImpl(bos);
        writer.writeCData("");
    }
}
