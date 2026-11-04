/*
 * @test
 * @summary Test Phaser constructor with parent Phaser
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        // Create a root phaser with 1 party
        Phaser root = new Phaser(1);

        // Create a child phaser with root as parent
        Phaser child = new Phaser(root);

        // The child should start with 0 registered parties
        if (child.getRegisteredParties() != 0) {
            throw new AssertionError("Expected 0 registered parties in child, got " + child.getRegisteredParties());
        }

        // The root should still have 1 registered party
        if (root.getRegisteredParties() != 1) {
            throw new AssertionError("Expected 1 registered party in root, got " + root.getRegisteredParties());
        }

        // The child's parent should be root (indirectly observable via arrival phase behavior)
        // Arrive and await advance on root to advance phase
        int rootPhaseBefore = root.getPhase();
        root.arriveAndDeregister();
        int rootPhaseAfter = root.getPhase();
        if (rootPhaseAfter != rootPhaseBefore + 1) {
            throw new AssertionError("Root phase did not advance as expected");
        }

        // Child phase should be independent and start at 0
        if (child.getPhase() != 0) {
            throw new AssertionError("Child phase expected to start at 0");
        }

        System.out.println("Phaser parent constructor test passed.");
    }
}
