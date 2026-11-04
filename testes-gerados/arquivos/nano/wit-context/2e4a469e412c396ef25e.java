/* @test    @summary Test insert(int, int) for AbstractStringBuilder with invalid offset values to trigger StringIndexOutOfBoundsException    @run main AbstractStringBuilderWitupTest */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithInvalidOffset();
    }

    private static void testInsertWithInvalidOffset() {
        AbstractStringBuilder builder = new AbstractStringBuilder(10) {
            @Override
            public AbstractStringBuilder insert(int offset, int i) {
                return super.insert(offset, i);
            }
        };
        builder.count = 5; // set a valid count
        try {
            builder.insert(-1, 123);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for offset -1");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected exception for offset -1");
        }
        try {
            builder.insert(6, 123);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for offset 6");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected exception for offset 6");
        }
        try {
            builder.insert(0, 123); // valid case
            System.out.println("Passed: Valid insert at offset 0");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Failed: Unexpected exception at offset 0");
        }
    }
}
