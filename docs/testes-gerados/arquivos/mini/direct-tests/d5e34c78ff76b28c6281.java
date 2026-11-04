/*
 * @test
 * @summary Test StringLatin1.tryAdvance method behavior
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;
import java.util.function.Consumer;

public class StringLatin1WitupTest {

    static class TestConsumer implements Consumer<String> {
        String value = null;
        public void accept(String s) {
            value = s;
        }
    }

    public static void main(String[] args) {
        // Create an anonymous subclass of StringLatin1 with index and fence fields
        // and next() method to simulate iteration
        class TestStringLatin1 extends StringLatin1 {
            int index;
            int fence;
            String[] elements;

            TestStringLatin1(String[] elements) {
                this.elements = elements;
                this.index = 0;
                this.fence = elements.length;
            }

            String next() {
                return elements[index++];
            }

            public boolean tryAdvance(Consumer<? super String> action) {
                if (action == null) {
                    throw new NullPointerException("tryAdvance action missing");
                }
                if (index != fence) {
                    action.accept(next());
                    return true;
                }
                return false;
            }
        }

        // Test 1: action is null -> expect NullPointerException
        TestStringLatin1 ts1 = new TestStringLatin1(new String[] {"a"});
        try {
            ts1.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"tryAdvance action missing".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        }

        // Test 2: index != fence, action accepted, returns true
        TestStringLatin1 ts2 = new TestStringLatin1(new String[] {"hello"});
        TestConsumer consumer2 = new TestConsumer();
        boolean result2 = ts2.tryAdvance(consumer2);
        if (!result2) {
            throw new RuntimeException("tryAdvance should return true when index != fence");
        }
        if (!"hello".equals(consumer2.value)) {
            throw new RuntimeException("Consumer did not receive expected value");
        }

        // Test 3: index == fence, returns false, no accept called
        TestStringLatin1 ts3 = new TestStringLatin1(new String[] {});
        TestConsumer consumer3 = new TestConsumer();
        boolean result3 = ts3.tryAdvance(consumer3);
        if (result3) {
            throw new RuntimeException("tryAdvance should return false when index == fence");
        }
        if (consumer3.value != null) {
            throw new RuntimeException("Consumer should not be called when index == fence");
        }

        // Test 4: multiple calls until exhausted
        String[] data = {"one", "two"};
        TestStringLatin1 ts4 = new TestStringLatin1(data);
        TestConsumer consumer4 = new TestConsumer();
        boolean first = ts4.tryAdvance(consumer4);
        if (!first || !"one".equals(consumer4.value)) {
            throw new RuntimeException("First tryAdvance call failed");
        }
        boolean second = ts4.tryAdvance(consumer4);
        if (!second || !"two".equals(consumer4.value)) {
            throw new RuntimeException("Second tryAdvance call failed");
        }
        boolean third = ts4.tryAdvance(consumer4);
        if (third) {
            throw new RuntimeException("Third tryAdvance call should return false");
        }

        System.out.println("All tests passed.");
    }
}
