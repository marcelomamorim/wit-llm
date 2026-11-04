/*
 * @test
 * @summary Verifies observable state after constructing a Phaser with a parent via Phaser(Phaser parent)
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        Phaser parent = new Phaser();
        Phaser child = new Phaser(parent);

        check(child.getParent() == parent, "parent must be the one passed to the constructor");
        check(child.getRoot() == parent.getRoot(), "root of child must equal root of parent");
        check(child.getRegisteredParties() == 0, "child must start with 0 registered parties");
        check(child.getUnarrivedParties() == 0, "child must start with 0 unarrived parties");
        check(child.getPhase() == 0, "child must start at phase 0");
        check(parent.getRegisteredParties() == 0, "parent must not be affected by child construction");

        Phaser root = new Phaser();
        check(root.getParent() == null, "Phaser() must have no parent");
        check(root.getRoot() == root, "root Phaser must be its own root");

        Phaser grandChild = new Phaser(child);
        check(grandChild.getParent() == child, "grandchild parent must be child");
        check(grandChild.getRoot() == parent, "grandchild root must be the top-level phaser");
        check(grandChild.getRegisteredParties() == 0, "grandchild must start with 0 registered parties");
    }
}
