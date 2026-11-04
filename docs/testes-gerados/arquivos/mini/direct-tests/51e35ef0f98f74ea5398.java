/*
 * @test
 * @summary Test java.util.concurrent.Phaser constructor with parent and parties
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        testConstructorWithNullParentZeroParties();
        testConstructorWithNullParentNonZeroParties();
        testConstructorWithParentZeroParties();
        testConstructorWithParentNonZeroParties();
        testConstructorWithIllegalParties();
    }

    static void testConstructorWithNullParentZeroParties() {
        Phaser phaser = new Phaser(null, 0);
        // phase should be 0, parties 0, state non-empty
        long state = getState(phaser);
        if ((state & 0xFFFFL) != 0) {
            throw new Error("Expected 0 parties in state");
        }
    }

    static void testConstructorWithNullParentNonZeroParties() {
        int parties = 3;
        Phaser phaser = new Phaser(null, parties);
        long state = getState(phaser);
        int extractedParties = (int)(state & 0xFFFFL);
        if (extractedParties != parties) {
            throw new Error("Expected parties " + parties + " but got " + extractedParties);
        }
    }

    static void testConstructorWithParentZeroParties() {
        Phaser parent = new Phaser(null, 0);
        Phaser phaser = new Phaser(parent, 0);
        long state = getState(phaser);
        int extractedParties = (int)(state & 0xFFFFL);
        if (extractedParties != 0) {
            throw new Error("Expected 0 parties but got " + extractedParties);
        }
        if (phaser.root != parent.root) {
            throw new Error("Child root should be same as parent root");
        }
    }

    static void testConstructorWithParentNonZeroParties() {
        Phaser parent = new Phaser(null, 1);
        Phaser phaser = new Phaser(parent, 1);
        long state = getState(phaser);
        int extractedParties = (int)(state & 0xFFFFL);
        if (extractedParties != 1) {
            throw new Error("Expected 1 party but got " + extractedParties);
        }
        if (phaser.root != parent.root) {
            throw new Error("Child root should be same as parent root");
        }
    }

    static void testConstructorWithIllegalParties() {
        try {
            // parties shifted right by PARTIES_SHIFT must be zero, so use a large number
            int illegalParties = 1 << 16; // 65536
            new Phaser(null, illegalParties);
            throw new Error("Expected IllegalArgumentException for illegal parties");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    // Reflection to get private state field
    static long getState(Phaser phaser) {
        try {
            java.lang.reflect.Field stateField = Phaser.class.getDeclaredField("state");
            stateField.setAccessible(true);
            return stateField.getLong(phaser);
        } catch (Exception e) {
            throw new Error("Failed to get state field", e);
        }
    }
}