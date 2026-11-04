/*
 * @test
 * @summary Verifies MessageFormat.setFormat stores formats at valid indices and throws ArrayIndexOutOfBoundsException for indices beyond the pattern's maximum offset
 * @run main MessageFormatWitupTest
 */

import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MessageFormatWitupTest {

    public static void main(String[] args) throws Exception {
        testSetFormatStoresFormatAtValidIndex();
        testSetFormatAboveMaxOffsetThrows();
        testSetFormatOnPatternWithoutElementsThrows();
        testSetFormatNegativeIndexThrows();
    }

    static void testSetFormatStoresFormatAtValidIndex() {
        MessageFormat mf = new MessageFormat("{0} and {1}", Locale.US);
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        mf.setFormat(1, nf);
        Format[] formats = mf.getFormats();
        if (formats.length != 2) {
            throw new RuntimeException("Unexpected formats length: " + formats.length);
        }
        if (formats[1] != nf) {
            throw new RuntimeException("Format at index 1 was not stored");
        }
    }

    static void testSetFormatAboveMaxOffsetThrows() {
        final MessageFormat mf = new MessageFormat("{0} and {1}", Locale.US);
        expectAioobe(() -> mf.setFormat(2, NumberFormat.getInstance(Locale.US)));
    }

    static void testSetFormatOnPatternWithoutElementsThrows() {
        final MessageFormat mf = new MessageFormat("abc", Locale.US);
        expectAioobe(() -> mf.setFormat(0, NumberFormat.getInstance(Locale.US)));
    }

    static void testSetFormatNegativeIndexThrows() {
        final MessageFormat mf = new MessageFormat("{0}", Locale.US);
        expectAioobe(() -> mf.setFormat(-1, NumberFormat.getInstance(Locale.US)));
    }

    static void expectAioobe(Runnable r) {
        try {
            r.run();
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected ArrayIndexOutOfBoundsException");
    }
}
