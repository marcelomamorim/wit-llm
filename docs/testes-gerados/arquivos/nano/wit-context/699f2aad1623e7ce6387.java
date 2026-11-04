/*
 * @test
 * @summary Tests for the constructor Phaser(Phaser parent) in java.util.concurrent.Phaser
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullParent();
        testConstructorWithValidParent();
    }

    private static void testConstructorWithNullParent() {
        try {
            Phaser p = new Phaser((Phaser) null);
            System.out.println("testConstructorWithNullParent: PASSED");
        } catch (Exception e) {
            System.out.println("testConstructorWithNullParent: FAILED - " + e);
        }
    }

    private static void testConstructorWithValidParent() {
        try {
            Phaser parent = new Phaser();
            Phaser p = new Phaser(parent);
            if (p != null) {
                System.out.println("testConstructorWithValidParent: PASSED");
            } else {
                System.out.println("testConstructorWithValidParent: FAILED - null returned");
            }
        } catch (Exception e) {
            System.out.println("testConstructorWithValidParent: FAILED - " + e);
        }
    }
}
