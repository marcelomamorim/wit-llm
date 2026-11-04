/*
 * @test
 * @summary Tests the public Phaser(Phaser parent, int parties) constructor behavior
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testRootWithParties() {
        Phaser p = new Phaser(null, 3);
        check(p.getRegisteredParties() == 3, "root registered parties should be 3");
        check(p.getPhase() == 0, "root phase should be 0");
        check(p.getParent() == null, "root parent should be null");
        check(p.getRoot() == p, "root of a root phaser should be itself");
    }

    static void testRootZeroParties() {
        Phaser p = new Phaser(null, 0);
        check(p.getRegisteredParties() == 0, "zero-party root should have 0 registered parties");
        check(p.getParent() == null, "root parent should be null");
        check(p.getRoot() == p, "root of a root phaser should be itself");
    }

    static void testChildRegistersWithParent() {
        Phaser parent = new Phaser();
        Phaser child = new Phaser(parent, 2);
        check(child.getRegisteredParties() == 2, "child should have 2 registered parties");
        check(child.getParent() == parent, "child parent should be the given parent");
        check(child.getRoot() == parent.getRoot(), "child root should equal parent root");
        check(parent.getRegisteredParties() == 1, "parent should gain one registered party from child");
    }

    static void testChildZeroPartiesDoesNotRegister() {
        Phaser parent = new Phaser();
        Phaser child = new Phaser(parent, 0);
        check(child.getRegisteredParties() == 0, "zero-party child should have 0 registered parties");
        check(parent.getRegisteredParties() == 0, "parent should not be registered by zero-party child");
    }

    static void testIllegalPartiesRejected() {
        boolean thrown = false;
        try {
            new Phaser(null, -1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "negative parties should throw IllegalArgumentException");

        thrown = false;
        try {
            new Phaser(new Phaser(), 65536);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "parties 65536 should throw IllegalArgumentException");
    }

    static void testMaxPartiesAccepted() {
        Phaser p = new Phaser(null, 65535);
        check(p.getRegisteredParties() == 65535, "65535 parties should be accepted");
    }

    public static void main(String[] args) {
        testRootWithParties();
        testRootZeroParties();
        testChildRegistersWithParent();
        testChildZeroPartiesDoesNotRegister();
        testIllegalPartiesRejected();
        testMaxPartiesAccepted();
    }
}
