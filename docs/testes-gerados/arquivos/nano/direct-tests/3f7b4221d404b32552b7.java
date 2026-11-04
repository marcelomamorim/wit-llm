/* @test    @summary Test Nodes.begin method for valid and invalid sizes    @run main NodesWitupTest */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithExceedingSize();
    }

    static void testBeginWithValidSize() {
        Nodes node = new Nodes();
        // Reflection or direct instantiation is not possible as Nodes is final with private constructor,
        // so we simulate the call via subclass or dummy subclass if needed.
        // Since the method is public, we can invoke it via an anonymous subclass or dummy class.
        class TestNode extends Nodes {
            public void testBegin(long size) {
                begin(size);
            }
        }
        TestNode testNode = new TestNode();
        try {
            testNode.testBegin(10L); // Valid size, should not throw
            System.out.println("testBeginWithValidSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithValidSize failed: " + e);
        }
    }

    static void testBeginWithExceedingSize() {
        Nodes node = new Nodes();
        class TestNode extends Nodes {
            public void testBegin(long size) {
                begin(size);
            }
        }
        TestNode testNode = new TestNode();
        try {
            testNode.testBegin(Long.MAX_VALUE); // Exceeds length, should throw
            System.err.println("testBeginWithExceedingSize failed: no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testBeginWithExceedingSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithExceedingSize failed: " + e);
        }
    }
}
