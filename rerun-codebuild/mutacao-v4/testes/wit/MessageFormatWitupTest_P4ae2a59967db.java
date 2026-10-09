/* @test
   @summary Testa o método setFormat para verificar exceções de ArrayIndexOutOfBoundsException.
   @run main MessageFormatWitupTest_P4ae2a59967db
 */
import java.text.MessageFormat;
import java.text.Format;
public class MessageFormatWitupTest_P4ae2a59967db {
    public static void main(String[] args) {
        testSetFormatThrowsException();
    }
    private static void testSetFormatThrowsException() {
        MessageFormat mf = new MessageFormat("Test {0}");
        int invalidIndex = 10; // assumindo que maxOffset < 10
        Format dummyFormat = null;
        try {
            mf.setFormat(invalidIndex, dummyFormat);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // esperado
        }
    }
}
