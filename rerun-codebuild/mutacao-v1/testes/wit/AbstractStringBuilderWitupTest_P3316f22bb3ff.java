/* @test    @summary Test offsetByCodePoints for IndexOutOfBoundsException on invalid index    @run main AbstractStringBuilderWitupTest_P3316f22bb3ff */

import java.lang.IndexOutOfBoundsException;

public class AbstractStringBuilderWitupTest_P3316f22bb3ff {

    public static void main(String[] args) {
        testOffsetByCodePointsIndexOutOfBounds();
    }

    public static void testOffsetByCodePointsIndexOutOfBounds() {
        java.lang.AbstractStringBuilder instance = new java.lang.AbstractStringBuilder(10) {
            @Override
            public int length() {
                return 10;
            }
        };
        try {
            // Test with index less than 0
            instance.offsetByCodePoints(-1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            // Test with index greater than count
            instance.offsetByCodePoints(11, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 11");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
