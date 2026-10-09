/* @test
   @summary Testa o método formatToCharacterIterator de MessageFormat com entradas válidas e inválidas.
   @run main MessageFormatWitupTest_P64edaa5a9570
 */
import java.text.AttributedCharacterIterator;
import java.text.MessageFormat;
import java.util.Locale;

public class MessageFormatWitupTest_P64edaa5a9570 {
    public static void main(String[] args) {
        testNullArguments();
        testValidArguments();
    }

    private static void testNullArguments() {
        try {
            MessageFormat mf = new MessageFormat("Test {0}");
            mf.formatToCharacterIterator(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testValidArguments() {
        MessageFormat mf = new MessageFormat("Hello {0}");
        Object[] args = {"World"};
        AttributedCharacterIterator iterator = mf.formatToCharacterIterator(args);
        if (iterator == null) {
            throw new AssertionError("Returned iterator is null");
        }
        StringBuilder sb = new StringBuilder();
        iterator.first();
        for (char c = iterator.current(); c != AttributedCharacterIterator.DONE; c = iterator.next()) {
            sb.append(c);
        }
        if (!sb.toString().contains("Hello")) {
            throw new AssertionError("Output does not contain expected text");
        }
    }
}
