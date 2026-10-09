/* @test
   @summary Verifies MessageFormat.formatToCharacterIterator with valid arguments.
   @run main MessageFormatWitupTest_P64edaa5a9570
 */
import java.text.MessageFormat;
import java.text.AttributedCharacterIterator;
import java.util.Objects;

public class MessageFormatWitupTest_P64edaa5a9570 {
    public static void main(String[] args) {
        testFormatToCharacterIteratorWithValidArguments();
        testFormatToCharacterIteratorWithNullArguments();
    }

    private static void testFormatToCharacterIteratorWithValidArguments() {
        String pattern = "Hello, {0}!";
        MessageFormat mf = new MessageFormat(pattern);
        Object[] args = {"World"};
        try {
            AttributedCharacterIterator iterator = mf.formatToCharacterIterator(args);
            Objects.requireNonNull(iterator, "Returned iterator should not be null");
            StringBuilder sb = new StringBuilder();
            for (char c = iterator.first(); c != AttributedCharacterIterator.DONE; c = iterator.next()) {
                sb.append(c);
            }
            String resultString = sb.toString();
            if (!resultString.contains("Hello, World!")) {
                throw new AssertionError("Expected output to contain 'Hello, World!', but got: " + resultString);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }

    private static void testFormatToCharacterIteratorWithNullArguments() {
        String pattern = "Test";
        MessageFormat mf = new MessageFormat(pattern);
        try {
            mf.formatToCharacterIterator(null);
            throw new AssertionError("Expected NullPointerException for null arguments");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
