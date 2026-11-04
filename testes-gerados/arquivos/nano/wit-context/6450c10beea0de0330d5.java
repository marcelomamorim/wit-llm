/*
 * @test
 * @summary Test getStoredContent method for null provider case
 * @run main CompressedResourceHeaderWitupTest
 */

import java.nio.ByteOrder;
import java.util.Objects;
import jdk.internal.jimage.decompressor.CompressedResourceHeader;
import jdk.internal.jimage.decompressor.ResourceDecompressor;

public class CompressedResourceHeaderWitupTest {

    public static void main(String[] args) {
        testGetStoredContent_NullProvider();
    }

    public static void testGetStoredContent_NullProvider() {
        CompressedResourceHeader header = new CompressedResourceHeader(100L, 200L, 10, 20, false);
        try {
            header.getStoredContent(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
