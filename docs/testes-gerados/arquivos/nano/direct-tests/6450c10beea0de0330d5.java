/* @test    @summary Test getStoredContent method of CompressedResourceHeader for various contentOffset values    @run main CompressedResourceHeaderWitupTest */

import java.nio.ByteOrder;
import java.util.Objects;
import jdk.internal.jimage.decompressor.CompressedResourceHeader;

public class CompressedResourceHeaderWitupTest {

    public static void main(String[] args) {
        testGetStoredContentWithValidOffset();
        testGetStoredContentWithNegativeOffset();
        testGetStoredContentWithNullProvider();
    }

    static void testGetStoredContentWithValidOffset() {
        String dummyString = "testString";
        int offset = 10;
        // Mock provider
        class MockProvider {
            String getString(int off) {
                if (off == offset) {
                    return dummyString;
                }
                return null;
            }
        }
        MockProvider provider = new MockProvider();
        CompressedResourceHeader header = new CompressedResourceHeader(100L, 200L, 0, offset, false);
        String result = header.getStoredContent(provider::getString);
        if (!Objects.equals(result, dummyString)) {
            throw new AssertionError("Expected: " + dummyString + ", but got: " + result);
        }
    }

    static void testGetStoredContentWithNegativeOffset() {
        class DummyProvider {
            String getString(int off) {
                return "should not be called";
            }
        }
        DummyProvider provider = new DummyProvider();
        CompressedResourceHeader header = new CompressedResourceHeader(100L, 200L, 0, -1, false);
        String result = header.getStoredContent(provider::getString);
        if (result != null) {
            throw new AssertionError("Expected null for contentOffset -1, but got: " + result);
        }
    }

    static void testGetStoredContentWithNullProvider() {
        try {
            CompressedResourceHeader header = new CompressedResourceHeader(100L, 200L, 0, 0, false);
            header.getStoredContent(null);
            throw new AssertionError("Expected NullPointerException for null provider");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
