/* @test    @summary Test offsetByCodePoints method of AbstractStringBuilder    @run main AbstractStringBuilderWitupTest_P68874a4b3425 */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest_P68874a4b3425 {

    public static void main(String[] args) {
        testOffsetByCodePoints();
    }

    public static void testOffsetByCodePoints() {
        class DummyStringBuilder extends AbstractStringBuilder {
            DummyStringBuilder(int capacity) {
                super(capacity);
            }
        }

        DummyStringBuilder builder = new DummyStringBuilder(10);
        builder.count = 5;
        try {
            int result = builder.offsetByCodePoints(2, 3);
            System.out.println("Offset result: " + result);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException thrown unexpectedly.");
        }

        // Test with index out of bounds (negative)
        try {
            builder.offsetByCodePoints(-1, 2);
            System.err.println("Expected IndexOutOfBoundsException for negative index, but none thrown.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Correctly caught IndexOutOfBoundsException for negative index.");
        }

        // Test with index out of bounds (greater than count)
        try {
            builder.offsetByCodePoints(6, 1);
            System.err.println("Expected IndexOutOfBoundsException for index > count, but none thrown.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Correctly caught IndexOutOfBoundsException for index > count.");
        }
    }
}
