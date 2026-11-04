/*
 * @test
 * @summary Test CompressedResourceHeader.getStoredContent behavior
 * @run main CompressedResourceHeaderWitupTest
 */

import java.util.Objects;
import jdk.internal.jimage.decompressor.CompressedResourceHeader;
import jdk.internal.jimage.decompressor.ResourceDecompressor.StringsProvider;

public class CompressedResourceHeaderWitupTest {

    static class DummyStringsProvider implements StringsProvider {
        private final int expectedOffset;
        private final String returnValue;

        DummyStringsProvider(int expectedOffset, String returnValue) {
            this.expectedOffset = expectedOffset;
            this.returnValue = returnValue;
        }

        @Override
        public String getString(int offset) {
            if (offset == expectedOffset) {
                return returnValue;
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        testNullProvider();
        testContentOffsetMinusOne();
        testValidContentOffset();
    }

    private static void testNullProvider() {
        CompressedResourceHeader header = new CompressedResourceHeader(10L, 20L, 5, 3, false);
        try {
            header.getStoredContent(null);
            throw new RuntimeException("Expected NullPointerException for null provider");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testContentOffsetMinusOne() {
        CompressedResourceHeader header = new CompressedResourceHeader(10L, 20L, 5, -1, false);
        StringsProvider provider = new DummyStringsProvider(5, "shouldNotBeCalled");
        String result = header.getStoredContent(provider);
        if (result != null) {
            throw new RuntimeException("Expected null when contentOffset is -1");
        }
    }

    private static void testValidContentOffset() {
        int offset = 7;
        String expectedString = "testString";
        CompressedResourceHeader header = new CompressedResourceHeader(10L, 20L, 5, offset, false);
        StringsProvider provider = new DummyStringsProvider(offset, expectedString);
        String result = header.getStoredContent(provider);
        if (!expectedString.equals(result)) {
            throw new RuntimeException("Expected returned string to match provider's string");
        }
    }
}
