/*
 * @test
 * @summary Witup-derived checks for Phaser(int parties): valid range and IllegalArgumentException for parties beyond 16-bit range
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        checkValid(0);
        checkValid(1);
        checkValid(2);
        checkValid(65535);
        checkIllegal(65536);
        checkIllegal(Integer.MAX_VALUE);
        checkIllegal(-1);
        checkIllegal(Integer.MIN_VALUE);
        System.out.println("PASS");
    }

    static void checkValid(int parties) {
        Phaser p = new Phaser(parties);
        if (p.getRegisteredParties() != parties) {
            throw new RuntimeException("registered parties mismatch for " + parties);
        }
        if (p.getPhase() != 0) {
            throw new RuntimeException("initial phase is not 0 for " + parties);
        }
        if (p.getParent() != null) {
            throw new RuntimeException("root phaser has a parent for " + parties);
        }
        if (p.getRoot() != p) {
            throw new RuntimeException("root of root phaser is not itself for " + parties);
        }
    }

    static void checkIllegal(int parties) {
        try {
            new Phaser(parties);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for " + parties);
    }
}
