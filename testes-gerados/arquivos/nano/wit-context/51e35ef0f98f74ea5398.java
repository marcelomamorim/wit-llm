/*
 * @test
 * @summary Tests for java.util.concurrent.Phaser constructor with parent and parties
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParentAndParties();
        testConstructorWithInvalidParties();
    }

    private static void testConstructorWithValidParentAndParties() {
        Phaser parent = new Phaser();
        try {
            Phaser p = new Phaser(parent, 2);
            System.out.println("testConstructorWithValidParentAndParties passed");
        } catch (Exception e) {
            System.err.println("testConstructorWithValidParentAndParties failed: " + e);
        }
    }

    private static void testConstructorWithInvalidParties() {
        try {
            // This should throw IllegalArgumentException because parties > (1 << 16) - 1
            int invalidParties = (1 << 16); // 65536
            new Phaser(null, invalidParties);
            System.err.println("testConstructorWithInvalidParties failed: exception not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testConstructorWithInvalidParties passed");
        } catch (Exception e) {
            System.err.println("testConstructorWithInvalidParties failed: " + e);
        }
    }
}
