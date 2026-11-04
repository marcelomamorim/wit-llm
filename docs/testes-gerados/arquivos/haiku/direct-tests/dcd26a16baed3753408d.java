/*
 * @test
 * @summary Tests MessageFormat.setFormat(int, Format) for valid, null, and out-of-range indexes
 * @run main MessageFormatWitupTest
 */

import java.text.FieldPosition;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParsePosition;

public class MessageFormatWitupTest {

    public static void main(String[] args) {
        testSetFormatReplacesElement();
        testNullResetsToDefaultFormatting();
        testIndexAboveMaxThrows();
        testNegativeIndexThrows();
        testPatternWithoutElementsThrows();
    }

    static Format constant(final String s) {
        return new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                return toAppendTo.append(s);
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        };
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetFormatReplacesElement() {
        MessageFormat mf = new MessageFormat("[{0}]");
        Format f = constant("X");
        mf.setFormat(0, f);
        check(mf.getFormats()[0] == f, "format was not stored at index 0");
        check("[X]".equals(mf.format(new Object[]{"a"})), "custom format not used");
    }

    static void testNullResetsToDefaultFormatting() {
        MessageFormat mf = new MessageFormat("[{0}]");
        mf.setFormat(0, constant("X"));
        mf.setFormat(0, null);
        check(mf.getFormats()[0] == null, "null format not stored");
        check("[abc]".equals(mf.format(new Object[]{"abc"})), "default formatting not restored");
    }

    static void testIndexAboveMaxThrows() {
        MessageFormat mf = new MessageFormat("{0}");
        boolean thrown = false;
        try {
            mf.setFormat(1, constant("X"));
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException for index above max offset");
    }

    static void testNegativeIndexThrows() {
        MessageFormat mf = new MessageFormat("{0}");
        boolean thrown = false;
        try {
            mf.setFormat(-1, constant("X"));
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException for negative index");
    }

    static void testPatternWithoutElementsThrows() {
        MessageFormat mf = new MessageFormat("plain text");
        boolean thrown = false;
        try {
            mf.setFormat(0, constant("X"));
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException when pattern has no format elements");
    }
}
