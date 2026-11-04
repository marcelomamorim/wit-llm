/* @test    @summary Test Nodes.begin with matching size parameter    @run main NodesWitupTest */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testBeginWithMatchingSize();
        testBeginWithNonMatchingSize();
    }

    static void testBeginWithMatchingSize() {
        int[] array = new int[10];
        Nodes node = new Nodes(array);
        try {
            node.begin(10);
            System.out.println("testBeginWithMatchingSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithMatchingSize failed: " + e);
        }
    }

    static void testBeginWithNonMatchingSize() {
        int[] array = new int[10];
        Nodes node = new Nodes(array);
        try {
            node.begin(5);
            System.err.println("testBeginWithNonMatchingSize failed: no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testBeginWithNonMatchingSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithNonMatchingSize failed: " + e);
        }
    }
}
