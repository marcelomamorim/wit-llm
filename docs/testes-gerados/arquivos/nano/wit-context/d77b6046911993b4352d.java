/*
 * @test
 * @summary Test insert(int, float) method for AbstractStringBuilder
 * @run main AbstractStringBuilderWitupTest
 */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithValidOffset();
        testInsertWithNegativeOffset();
        testInsertWithOffsetGreaterThanLength();
    }

    private static void testInsertWithValidOffset() {
        try {
            AbstractStringBuilder asb = new DummyAbstractStringBuilder(10);
            asb.insert(0, 1.23f);
            System.out.println("testInsertWithValidOffset: PASSED");
        } catch (Exception e) {
            System.out.println("testInsertWithValidOffset: FAILED - " + e);
        }
    }

    private static void testInsertWithNegativeOffset() {
        try {
            AbstractStringBuilder asb = new DummyAbstractStringBuilder(10);
            asb.insert(-1, 4.56f);
            System.out.println("testInsertWithNegativeOffset: FAILED - no exception");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("testInsertWithNegativeOffset: PASSED");
        } catch (Exception e) {
            System.out.println("testInsertWithNegativeOffset: FAILED - " + e);
        }
    }

    private static void testInsertWithOffsetGreaterThanLength() {
        try {
            AbstractStringBuilder asb = new DummyAbstractStringBuilder(10);
            asb.insert(11, 7.89f);
            System.out.println("testInsertWithOffsetGreaterThanLength: FAILED - no exception");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("testInsertWithOffsetGreaterThanLength: PASSED");
        } catch (Exception e) {
            System.out.println("testInsertWithOffsetGreaterThanLength: FAILED - " + e);
        }
    }

    static class DummyAbstractStringBuilder extends AbstractStringBuilder {
        DummyAbstractStringBuilder(int capacity) {
            super(capacity);
        }
        @Override
        public AbstractStringBuilder insert(int offset, float f) {
            return super.insert(offset, f);
        }
    }
}
