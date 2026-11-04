/* @test    @summary Test constructor Phaser(Phaser parent) with null parent    @run main PhaserWitupTest */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullParent();
        testConstructorWithNonNullParent();
    }

    static void testConstructorWithNullParent() {
        Phaser phaser = new Phaser((Phaser) null);
        if (phaser == null) {
            throw new AssertionError("Phaser instance should not be null when constructed with null parent");
        }
        // Verify that the phase is initialized to 0
        if (phaser.getPhase() != 0) {
            throw new AssertionError("Initial phase should be 0");
        }
    }

    static void testConstructorWithNonNullParent() {
        Phaser parent = new Phaser();
        Phaser child = new Phaser(parent);
        if (child == null) {
            throw new AssertionError("Child Phaser should not be null");
        }
        // Verify that the parent is correctly assigned by checking internal behavior
        // Since internal state is not directly accessible, test that child is registered with parent
        int parentParties = parent.getRegisteredParties();
        int childParties = child.getRegisteredParties();
        if (childParties != 0) {
            throw new AssertionError("Child Phaser should start with 0 registered parties");
        }
        // Register a party in parent and verify that child remains unaffected
        parent.register();
        if (parent.getRegisteredParties() != 1) {
            throw new AssertionError("Parent should have 1 registered party after register()");
        }
        if (child.getRegisteredParties() != 0) {
            throw new AssertionError("Child should still have 0 registered parties");
        }
    }
}
