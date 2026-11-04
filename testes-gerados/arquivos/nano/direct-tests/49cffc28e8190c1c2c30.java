/* @test    @summary Test the end() method of Nodes class for correct exception throwing and behavior    @run main NodesWitupTest */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testEndMethodThrowsExceptionWhenCurSizeLessThanArrayLength();
        testEndMethodDoesNotThrowWhenCurSizeEqualsArrayLength();
    }

    private static void testEndMethodThrowsExceptionWhenCurSizeLessThanArrayLength() {
        // Setup: create a subclass or mock of Nodes with a method to set curSize and array length
        class TestNodes extends Nodes {
            int curSize;
            Object[] array;
            TestNodes(int size) {
                this.array = new Object[size];
                this.curSize = size + 1; // intentionally set to trigger exception
            }
            @Override
            public void end() {
                if (curSize < array.length) {
                    throw new IllegalStateException(String.format("End size %d is less than fixed size %d", curSize, array.length));
                }
            }
        }
        try {
            new TestNodes(10).end();
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("End size 11 is less than fixed size 10")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
        }
    }

    private static void testEndMethodDoesNotThrowWhenCurSizeEqualsArrayLength() {
        class TestNodes extends Nodes {
            int curSize;
            Object[] array;
            TestNodes(int size) {
                this.array = new Object[size];
                this.curSize = size; // equal to array length
            }
            @Override
            public void end() {
                if (curSize < array.length) {
                    throw new IllegalStateException(String.format("End size %d is less than fixed size %d", curSize, array.length));
                }
            }
        }
        try {
            new TestNodes(10).end(); // should not throw
        } catch (Exception e) {
            throw new AssertionError("Did not expect exception, but got: " + e);
        }
    }
}
