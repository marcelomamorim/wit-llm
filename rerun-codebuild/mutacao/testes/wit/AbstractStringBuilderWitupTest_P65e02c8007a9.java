/* @test    @summary Test for AbstractStringBuilder.offsetByCodePoints() method handling of index bounds    @run main AbstractStringBuilderWitupTest_P65e02c8007a9 */

import java.lang.IndexOutOfBoundsException;

public class AbstractStringBuilderWitupTest_P65e02c8007a9 {

    public static void main(String[] args) {
        testOffsetByCodePointsWithInvalidIndex();
    }

    private static void testOffsetByCodePointsWithInvalidIndex() {
        java.lang.AbstractStringBuilder builder = new java.lang.AbstractStringBuilder(10) {
            @Override
            public int length() {
                return 10;
            }
        };
        // Test with index less than 0
        try {
            builder.offsetByCodePoints(-1, 1);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for index -1");
        }
        // Test with index greater than count
        try {
            builder.offsetByCodePoints(11, 1);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for index 11");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for index 11");
        }
    }
}
