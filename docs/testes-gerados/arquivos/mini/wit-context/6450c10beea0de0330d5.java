/*
 * @test
 * @summary Test CompressedResourceHeader.getStoredContent behavior including null provider and contentOffset -1
 * @run main CompressedResourceHeaderWitupTest
 */

import java.util.Objects;
import jdk.internal.jimage.decompressor.CompressedResourceHeader;
import jdk.internal.jimage.decompressor.ResourceDecompressor.StringsProvider;

public class CompressedResourceHeaderWitupTest {

    static class DummyStringsProvider implements StringsProvider {
        private final String value;
        DummyStringsProvider(String value) {
            this.value = value;
        }
        @Override
        public String getString(int offset) {
            // For testing, ignore offset and return fixed value
            return value;
        }
    }

    public static void main(String[] args) {
        testNullProviderThrowsNPE();
        testContentOffsetMinusOneReturnsNull();
        testValidContentOffsetReturnsString();
    }

    private static void testNullProviderThrowsNPE() {
        CompressedResourceHeader header = new CompressedResourceHeader(10L, 20L, 1, 2, false);
        try {
            header.getStoredContent(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testContentOffsetMinusOneReturnsNull() {
        CompressedResourceHeader header = new CompressedResourceHeader(10L, 20L, 1, -1, false);
        StringsProvider provider = new DummyStringsProvider("dummy");
        String result = header.getStoredContent(provider);
        if (result != null) {
            throw new RuntimeException("Expected null when contentOffset is -1, got: " + result);
        }
    }

    private static void testValidContentOffsetReturnsString() {
        CompressedResourceHeader header = new CompressedResourceHeader(10L, 20L, 1, 5, false);
        StringsProvider provider = new DummyStringsProvider("testString");
        String result = header.getStoredContent(provider);
        if (!"testString".equals(result)) {
            throw new RuntimeException("Expected 'testString' but got: " + result);
        }
    }

}
