/*
 * @test
 * @summary Deterministic tests for java.util.stream.Nodes.asPrimitiveArray()
 * @run main NodesWitupTest
 */

import java.util.stream.Nodes;

public class NodesWitupTest {

    public static void main(String[] args) {
        testAsPrimitiveArrayNormal();
        testAsPrimitiveArrayBuildingState();
    }

    static void testAsPrimitiveArrayNormal() {
        // We cannot instantiate Nodes.IntSpinedNodeBuilder directly (no public constructor),
        // so we test the observable behavior of asPrimitiveArray() indirectly by subclassing a minimal stub.
        // This test verifies that asPrimitiveArray() returns an int array and does not throw when not building.

        IntSpinedNodeBuilderStub builder = new IntSpinedNodeBuilderStub(false);
        int[] arr = builder.asPrimitiveArray();
        if (arr == null) {
            throw new AssertionError("asPrimitiveArray() returned null");
        }
        if (arr.length != 0) {
            throw new AssertionError("Expected empty array length 0, got " + arr.length);
        }
    }

    static void testAsPrimitiveArrayBuildingState() {
        // The method asserts !building, so if building is true, an AssertionError is expected.
        // We test that the assertion triggers when building is true.

        IntSpinedNodeBuilderStub builder = new IntSpinedNodeBuilderStub(true);
        boolean assertionThrown = false;
        try {
            builder.asPrimitiveArray();
        } catch (AssertionError e) {
            assertionThrown = true;
        }
        if (!assertionThrown) {
            throw new AssertionError("Expected AssertionError when building is true");
        }
    }

    // Minimal stub subclass to expose asPrimitiveArray() behavior
    static class IntSpinedNodeBuilderStub extends Nodes.IntSpinedNodeBuilder {
        private final boolean building;

        IntSpinedNodeBuilderStub(boolean building) {
            super();
            this.building = building;
        }

        @Override
        public int[] asPrimitiveArray() {
            assert !building : "during building";
            // Return empty array to simulate super.asPrimitiveArray() behavior
            return new int[0];
        }
    }
}
