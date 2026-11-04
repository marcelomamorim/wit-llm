/*
 * @test    @summary Test StringLatin1.tryAdvance method for correct behavior
 * @run main StringLatin1WitupTest
 */

import java.util.function.Consumer;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithEmptyInput();
        testTryAdvanceWithSingleElement();
        testTryAdvanceWithMultipleElements();
    }

    static class DummyConsumer implements Consumer<String> {
        boolean accepted = false;
        String acceptedValue = null;
        @Override
        public void accept(String t) {
            accepted = true;
            acceptedValue = t;
        }
    }

    static void testTryAdvanceWithNullConsumer() {
        try {
            StringLatin1 stringLatin1 = new StringLatin1();
            stringLatin1.tryAdvance(null);
            System.out.println("FAIL: Expected NullPointerException for null consumer");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testTryAdvanceWithEmptyInput() {
        StringLatin1 stringLatin1 = new StringLatin1();
        // Simulate empty input by setting index == fence
        stringLatin1.index = 0;
        stringLatin1.fence = 0;
        DummyConsumer consumer = new DummyConsumer();
        boolean result = stringLatin1.tryAdvance(consumer);
        if (!result && !consumer.accepted) {
            System.out.println("PASS: tryAdvance returns false and does not accept input for empty input");
        } else {
            System.out.println("FAIL: tryAdvance should return false and not accept input for empty input");
        }
    }

    static void testTryAdvanceWithSingleElement() {
        StringLatin1 stringLatin1 = new StringLatin1();
        stringLatin1.index = 0;
        stringLatin1.fence = 1;
        DummyConsumer consumer = new DummyConsumer();
        boolean result = stringLatin1.tryAdvance(consumer);
        if (result && consumer.accepted && "test".equals(consumer.acceptedValue)) {
            System.out.println("PASS: tryAdvance processes single element correctly");
        } else {
            System.out.println("FAIL: tryAdvance failed on single element");
        }
    }

    static void testTryAdvanceWithMultipleElements() {
        StringLatin1 stringLatin1 = new StringLatin1();
        stringLatin1.index = 0;
        stringLatin1.fence = 3;
        DummyConsumer consumer1 = new DummyConsumer();
        boolean result1 = stringLatin1.tryAdvance(consumer1);
        DummyConsumer consumer2 = new DummyConsumer();
        boolean result2 = stringLatin1.tryAdvance(consumer2);
        if (result1 && consumer1.accepted && "test".equals(consumer1.acceptedValue) &&
            result2 && consumer2.accepted && "test".equals(consumer2.acceptedValue)) {
            System.out.println("PASS: tryAdvance processes multiple elements correctly");
        } else {
            System.out.println("FAIL: tryAdvance failed on multiple elements");
        }
    }
}
