/*
 * @test
 * @summary Test AttributedString.setIndex(int) for valid and invalid index bounds
 * @run main AttributedStringWitupTest
 */

import java.text.AttributedString;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.HashMap;
import java.util.Map;

public class AttributedStringWitupTest {
    public static void main(String[] args) {
        testSetIndexValid();
        testSetIndexInvalidBelowBegin();
        testSetIndexInvalidAboveEnd();
    }

    private static void testSetIndexValid() {
        String text = "hello world";
        Map<Attribute, Object> attrs = new HashMap<>();
        // Use empty attributes map for simplicity
        AttributedString as = new AttributedString(text, attrs);
        AttributedCharacterIterator it = as.getIterator();

        // The valid range is from beginIndex to endIndex inclusive
        int begin = it.getBeginIndex();
        int end = it.getEndIndex();

        // Test setIndex at beginIndex
        char cBegin = as.setIndex(begin);
        if (cBegin != text.charAt(begin)) {
            throw new RuntimeException("setIndex(beginIndex) returned wrong char");
        }

        // Test setIndex at endIndex
        // endIndex is one past last char, so setIndex(endIndex) should throw or be invalid
        // According to AttributedCharacterIterator contract, endIndex is exclusive
        // So setIndex(endIndex) is invalid and should throw IllegalArgumentException
        boolean threw = false;
        try {
            as.setIndex(end);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("setIndex(endIndex) did not throw IllegalArgumentException");
        }

        // Test setIndex at a valid middle position
        int mid = begin + (end - begin) / 2;
        if (mid == end) mid = end - 1; // ensure mid < end
        char cMid = as.setIndex(mid);
        if (cMid != text.charAt(mid)) {
            throw new RuntimeException("setIndex(mid) returned wrong char");
        }
    }

    private static void testSetIndexInvalidBelowBegin() {
        String text = "abc";
        AttributedString as = new AttributedString(text);
        AttributedCharacterIterator it = as.getIterator();
        int begin = it.getBeginIndex();

        boolean threw = false;
        try {
            as.setIndex(begin - 1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("setIndex(position < beginIndex) did not throw IllegalArgumentException");
        }
    }

    private static void testSetIndexInvalidAboveEnd() {
        String text = "abc";
        AttributedString as = new AttributedString(text);
        AttributedCharacterIterator it = as.getIterator();
        int end = it.getEndIndex();

        boolean threw = false;
        try {
            as.setIndex(end + 1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("setIndex(position > endIndex) did not throw IllegalArgumentException");
        }
    }
}
