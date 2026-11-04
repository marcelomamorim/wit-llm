/* @test    @summary Test the register() method of Phaser for correct registration count increment.    @run main PhaserWitupTest */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testRegisterIncrementsCount();
    }

    public static void testRegisterIncrementsCount() {
        Phaser phaser = new Phaser();
        int initialParties = phaser.getRegisteredParties();
        int newCount = phaser.register();
        int updatedParties = phaser.getRegisteredParties();
        if (newCount != initialParties + 1) {
            throw new AssertionError("register() did not return expected count: expected " + (initialParties + 1) + " but got " + newCount);
        }
        if (updatedParties != newCount) {
            throw new AssertionError("getRegisteredParties() does not match register() return: expected " + newCount + " but got " + updatedParties);
        }
        System.out.println("testRegisterIncrementsCount passed.");
    }
}
