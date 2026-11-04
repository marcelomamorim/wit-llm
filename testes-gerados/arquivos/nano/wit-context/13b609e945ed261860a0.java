/*
 * @test
 * @summary Test for java.util.concurrent.Phaser.register()
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testRegisterReturnsPositive();
        testRegisterMultipleTimes();
    }

    private static void testRegisterReturnsPositive() {
        Phaser phaser = new Phaser();
        int result = phaser.register();
        if (result <= 0) {
            throw new AssertionError("Expected positive result from register(), but got: " + result);
        }
    }

    private static void testRegisterMultipleTimes() {
        Phaser phaser = new Phaser();
        int first = phaser.register();
        int second = phaser.register();
        if (second != first + 1) {
            throw new AssertionError("Expected second register() to increment count by 1. First: " + first + ", Second: " + second);
        }
    }
}
