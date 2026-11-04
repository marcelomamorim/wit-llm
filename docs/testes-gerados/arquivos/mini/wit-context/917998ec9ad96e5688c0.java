/*
 * @test
 * @summary Test LinkedBlockingQueue.remove(Object) for expected behavior and exception path
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // Test remove(null) returns false without exception
        if (queue.remove(null)) {
            throw new Error("remove(null) should return false");
        }

        // Test remove(Object) with element not present returns false
        if (queue.remove("absent") != false) {
            throw new Error("remove(non-present) should return false");
        }

        // Test remove(Object) with element present returns true
        queue.add("test");
        if (!queue.remove("test")) {
            throw new Error("remove(present) should return true");
        }

        // Test remove(Object) with element present twice removes first occurrence only
        queue.add("dup");
        queue.add("dup");
        if (!queue.remove("dup")) {
            throw new Error("remove(dup) should return true");
        }
        // After removal, one "dup" should remain
        if (!queue.remove("dup")) {
            throw new Error("second remove(dup) should return true");
        }

        // After all removals, queue should be empty
        if (!queue.isEmpty()) {
            throw new Error("queue should be empty after removals");
        }

        System.out.println("All LinkedBlockingQueue.remove(Object) tests passed.");
    }
}
