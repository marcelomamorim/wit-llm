/*
 * @test
 * @summary Test for Object.wait(long, int) method exception paths
 * @run main ObjectWitupTest
 */

import java.lang.Object;

public class ObjectWitupTest {

    public static void main(String[] args) throws Exception {
        testWaitNegativeTimeout();
        testWaitInvalidNanos();
    }

    public static void testWaitNegativeTimeout() {
        Object obj = new Object();
        try {
            obj.wait(-1L, 0);
            System.out.println("Failed: Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for negative timeout");
        }
    }

    public static void testWaitInvalidNanos() {
        Object obj = new Object();
        try {
            obj.wait(0L, -1);
            System.out.println("Failed: Expected IllegalArgumentException for nanos < 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for nanos < 0");
        }
        try {
            obj.wait(0L, 1000000);
            System.out.println("Failed: Expected IllegalArgumentException for nanos > 999999");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for nanos > 999999");
        }
    }
}
