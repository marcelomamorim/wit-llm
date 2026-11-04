/*
 * @test
 * @summary Test for java.util.stream.Nodes.forEach method, focusing on null consumer handling.
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;
import java.util.function.Consumer;

public class NodesWitupTest {

    public static void main(String[] args) {
        testForEachWithNullConsumer();
    }

    public static void testForEachWithNullConsumer() {
        try {
            Nodes.forEach(null);
            System.out.println("FAIL: Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
