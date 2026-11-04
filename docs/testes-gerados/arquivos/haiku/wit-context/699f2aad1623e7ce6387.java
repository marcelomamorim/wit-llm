/*
 * @test
 * @summary Verifies observable state produced by the public Phaser(Phaser parent) constructor
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testNullParent();
        testChildOfRegisteredParent();
        testGrandchildUsesRootOfChain();
    }

    static void testNullParent() {
        Phaser p = new Phaser((Phaser) null);
        check(p.getParent() == null, "null parent must be reported as null");
        check(p.getRoot() == p, "a phaser without parent must be its own root");
        check(p.getRegisteredParties() == 0, "no parties must be registered with null parent");
        check(p.getPhase() == 0, "initial phase must be zero");
        check(!p.isTerminated(), "new phaser must not be terminated");
    }

    static void testChildOfRegisteredParent() {
        Phaser root = new Phaser(2);
        Phaser child = new Phaser(root);
        check(child.getParent() == root, "child parent must be the given parent");
        check(child.getRoot() == root, "child root must be the parent's root");
        check(child.getRegisteredParties() == 0, "child must start with zero registered parties");
        check(root.getRegisteredParties() == 2, "parent registration must be unchanged by child creation");
        check(child.getPhase() == 0, "child initial phase must be zero");
        check(!child.isTerminated(), "child must not be terminated");
    }

    static void testGrandchildUsesRootOfChain() {
        Phaser root = new Phaser(1);
        Phaser child = new Phaser(root);
        Phaser grandchild = new Phaser(child);
        check(grandchild.getParent() == child, "grandchild parent must be the child");
        check(grandchild.getRoot() == root, "grandchild root must be the top-level root");
        check(grandchild.getRegisteredParties() == 0, "grandchild must start with zero registered parties");
        check(root.getRegisteredParties() == 1, "root registration must be unchanged");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
