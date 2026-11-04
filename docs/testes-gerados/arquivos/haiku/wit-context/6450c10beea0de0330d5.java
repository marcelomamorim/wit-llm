/*
 * @test
 * @summary Verifies that getStoredContent rejects a null StringsProvider with NullPointerException, even when contentOffset is -1
 * @modules java.base/jdk.internal.jimage.decompressor
 * @run main CompressedResourceHeaderWitupTest
 */

import jdk.internal.jimage.decompressor.CompressedResourceHeader;

public class CompressedResourceHeaderWitupTest {

    public static void main(String[] args) {
        CompressedResourceHeader withOffset = new CompressedResourceHeader(1L, 2L, 3, 4, true);
        CompressedResourceHeader withoutContent = new CompressedResourceHeader(1L, 2L, 3, -1, true);

        expectNpe(withOffset);
        expectNpe(withoutContent);
    }

    private static void expectNpe(CompressedResourceHeader header) {
        try {
            header.getStoredContent(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("NullPointerException expected for null provider");
    }
}
