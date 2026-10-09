/* @test    @summary Test for AbstractStringBuilder.codePointBefore(int index) method, focusing on exception paths and normal behavior    @run main AbstractStringBuilderWitupTest_P2b3cd82cd177 */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest_P2b3cd82cd177 {

    public static void main(String[] args) {
        testCodePointBeforeValidIndex();
        testCodePointBeforeNegativeIndex();
        testCodePointBeforeIndexGreaterThanCount();
    }

    private static void testCodePointBeforeValidIndex() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        sb.value = new byte[] {65, 66, 67}; // 'A', 'B', 'C'
        sb.count = 3;
        int index = 2; // valid index
        int result = sb.codePointBefore(index);
        // Expecting ASCII value of 'B' which is 66
        if (result != 66) {
            throw new AssertionError("Expected 66, but got " + result);
        }
    }

    private static void testCodePointBeforeNegativeIndex() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        sb.value = new byte[] {65, 66, 67};
        sb.count = 3;
        int index = -1; // invalid index, should throw
        boolean thrown = false;
        try {
            sb.codePointBefore(index);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        }
    }

    private static void testCodePointBeforeIndexGreaterThanCount() {
        AbstractStringBuilder sb = new ConcreteStringBuilder();
        sb.value = new byte[] {65, 66, 67};
        sb.count = 3;
        int index = 4; // invalid index, should throw
        boolean thrown = false;
        try {
            sb.codePointBefore(index);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index > count");
        }
    }

    // Concrete subclass to instantiate AbstractStringBuilder
    static class ConcreteStringBuilder extends AbstractStringBuilder {
        // No additional implementation needed for testing
    }
}
