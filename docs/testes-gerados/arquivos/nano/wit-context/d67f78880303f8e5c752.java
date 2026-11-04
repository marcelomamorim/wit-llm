/*
 * @test
 * @summary Basic tests for java.util.concurrent.Phaser constructor
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
        testConstructorWithParent();
    }

    public static void testDefaultConstructor() {
        Phaser p = new Phaser();
        if (p == null) {
            throw new AssertionError("Phaser() returned null");
        }
        // Additional observable checks can be added if needed
    }

    public static void testConstructorWithParent() {
        Phaser parent = new Phaser();
        Phaser child = new Phaser(parent);
        if (child == null) {
            throw new AssertionError("Phaser(parent) returned null");
        }
        // Additional observable checks can be added if needed
    }
}
