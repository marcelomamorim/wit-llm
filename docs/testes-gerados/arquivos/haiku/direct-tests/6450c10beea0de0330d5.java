/*
 * @test
 * @summary Tests CompressedResourceHeader.getStoredContent behavior
 * @modules java.base/jdk.internal.jimage.decompressor
 * @run main CompressedResourceHeaderWitupTest
 */

import jdk.internal.jimage.decompressor.CompressedResourceHeader;
import jdk.internal.jimage.decompressor.ResourceDecompressor.StringsProvider;

public class CompressedResourceHeaderWitupTest {

    public static void main(String[] args) {
        testMinusOneReturnsNullWithoutCallingProvider();
        testReturnsStringFromProvider();
        testNullProviderThrowsNpeWithContent();
        testNullProviderThrowsNpeWithoutContent();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMinusOneReturnsNullWithoutCallingProvider() {
        CompressedResourceHeader header =
                new CompressedResourceHeader(10L, 20L, 0, -1, false);
        final int[] calls = {0};
        StringsProvider provider = new StringsProvider() {
            @Override
            public String getString(int offset) {
                calls[0]++;
                return "unused";
            }
        };
        String result = header.getStoredContent(provider);
        check(result == null, "expected null for contentOffset -1");
        check(calls[0] == 0, "provider must not be called for contentOffset -1");
    }

    static void testReturnsStringFromProvider() {
        CompressedResourceHeader header =
                new CompressedResourceHeader(10L, 20L, 0, 5, true);
        final int[] lastOffset = {-1};
        StringsProvider provider = new StringsProvider() {
            @Override
            public String getString(int offset) {
                lastOffset[0] = offset;
                return "hello";
            }
        };
        String result = header.getStoredContent(provider);
        check("hello".equals(result), "expected provider string 'hello'");
        check(lastOffset[0] == 5, "provider must be queried with contentOffset 5");
    }

    static void testNullProviderThrowsNpeWithContent() {
        CompressedResourceHeader header =
                new CompressedResourceHeader(10L, 20L, 0, 7, false);
        try {
            header.getStoredContent(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(provider)
        }
    }

    static void testNullProviderThrowsNpeWithoutContent() {
        CompressedResourceHeader header =
                new CompressedResourceHeader(10L, 20L, 0, -1, false);
        try {
            header.getStoredContent(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected: null check happens before the -1 check
        }
    }
}
