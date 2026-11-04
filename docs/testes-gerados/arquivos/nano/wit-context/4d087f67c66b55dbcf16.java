/*
 * @test
 * @summary Test writeEndElement() method for normal and exceptional behavior
 * @run main XMLStreamWriterImplWitupTest
 */

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import jdk.internal.util.xml.impl.XMLStreamWriterImpl;
import jdk.internal.util.xml.XMLStreamException;

public class XMLStreamWriterImplWitupTest {

    public static void main(String[] args) {
        testWriteEndElement_Normal();
        testWriteEndElement_Exception();
    }

    static void testWriteEndElement_Normal() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos);
            // Simulate a current element in start tag open state
            // Since _currentEle is package-private, assume test setup or reflection is needed.
            // For simplicity, test the normal path assuming _currentEle is not null.
            // Here, we focus on the observable output.
            // As we cannot access _currentEle directly, this test assumes the method is called in a valid state.
            // Alternatively, mock or extend class for testing purposes.
            // For demonstration, we invoke writeEndElement assuming proper state.
            writer.writeEndElement();
            // Verify output contains closing tag
            String output = baos.toString("UTF-8");
            if (!output.contains("</")) {
                throw new AssertionError("Expected closing tag in output");
            }
            System.out.println("testWriteEndElement_Normal passed.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in normal test", e);
        }
    }

    static void testWriteEndElement_Exception() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XMLStreamWriterImpl writer = new XMLStreamWriterImpl(baos);
            // Force _currentEle to null to trigger exception
            // Since _currentEle is package-private, assume test setup or reflection.
            // For simplicity, extend class to set _currentEle to null.
            XMLStreamWriterImpl testWriter = new XMLStreamWriterImpl(baos) {
                {
                    this._currentEle = null;
                }
            };
            try {
                testWriter.writeEndElement();
                throw new AssertionError("Expected XMLStreamException was not thrown");
            } catch (XMLStreamException e) {
                if (!e.getMessage().equals("No element was found to write")) {
                    throw new AssertionError("Unexpected exception message: " + e.getMessage());
                }
                System.out.println("testWriteEndElement_Exception passed.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in exception test", e);
        }
    }
}
