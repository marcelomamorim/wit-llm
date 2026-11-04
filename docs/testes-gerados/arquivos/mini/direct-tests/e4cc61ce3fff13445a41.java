/*
 * @test
 * @summary Test Nodes.asPrimitiveArray() method behavior
 * @run main NodesWitupTest
 */

public class NodesWitupTest {

    static class TestNode extends java.util.stream.Nodes {
        boolean building = false;
        double[] superArray = new double[] {1.0, 2.0, 3.0};

        // Provide a super.asPrimitiveArray() simulation
        public double[] asPrimitiveArray() {
            if (building) throw new AssertionError("during building");
            return superArray;
        }

        public double[] testAsPrimitiveArray() {
            assert !building : "during building";
            return super.asPrimitiveArray();
        }
    }

    public static void main(String[] args) {
        TestNode node = new TestNode();

        // Test normal behavior: building == false
        node.building = false;
        double[] result = node.testAsPrimitiveArray();
        if (result == null || result.length != 3 || result[0] != 1.0 || result[1] != 2.0 || result[2] != 3.0) {
            throw new RuntimeException("Unexpected result from asPrimitiveArray");
        }

        // Test assertion failure when building == true
        node.building = true;
        try {
            node.testAsPrimitiveArray();
            throw new RuntimeException("Expected AssertionError not thrown");
        } catch (AssertionError e) {
            if (!"during building".equals(e.getMessage())) {
                throw new RuntimeException("AssertionError message mismatch", e);
            }
        }

        System.out.println("Test passed");
    }
}
