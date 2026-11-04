/*
 * @test
 * @summary Test constructor Phaser(Phaser parent) for basic behavior and edge cases
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        testConstructorWithNullParent();
        testConstructorWithNonNullParent();
    }

    private static void testConstructorWithNullParent() {
        Phaser phaser = new Phaser((Phaser) null);
        // The phase should start at 0
        if (phaser.getPhase() != 0) {
            throw new AssertionError("Phase should start at 0");
        }
        // No parties registered initially
        if (phaser.getRegisteredParties() != 0) {
            throw new AssertionError("No parties should be registered initially");
        }
    }

    private static void testConstructorWithNonNullParent() {
        Phaser parent = new Phaser();
        Phaser child = new Phaser(parent);
        // Child should start at phase 0
        if (child.getPhase() != 0) {
            throw new AssertionError("Child phase should start at 0");
        }
        // Child initially has 0 registered parties
        if (child.getRegisteredParties() != 0) {
            throw new AssertionError("Child should have 0 registered parties initially");
        }
        // Parent should be unaffected
        if (parent.getRegisteredParties() != 0) {
            throw new AssertionError("Parent should have 0 registered parties initially");
        }
    }
}
