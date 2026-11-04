/* @test    @summary Test drainTo method of DelayQueue for correct behavior and regression protection    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.Collection;
import java.util.ArrayList;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testDrainToEmptyQueue();
        testDrainToWithElements();
        testDrainToLimitedCapacity();
    }

    private static void testDrainToEmptyQueue() {
        DelayQueue<Object> dq = new DelayQueue<>();
        Collection<Object> c = new ArrayList<>();
        int drainedCount = dq.drainTo(c);
        assert drainedCount == 0 : "drainTo on empty queue should return 0";
        assert c.isEmpty() : "Collection should remain empty after drainTo on empty queue";
    }

    private static void testDrainToWithElements() {
        DelayQueue<String> dq = new DelayQueue<>();
        dq.offer("element1");
        dq.offer("element2");
        Collection<String> c = new ArrayList<>();
        int drainedCount = dq.drainTo(c);
        assert drainedCount == 2 : "drainTo should drain all elements";
        assert c.contains("element1") : "Collection should contain element1";
        assert c.contains("element2") : "Collection should contain element2";
        assert dq.isEmpty() : "Queue should be empty after drainTo";
    }

    private static void testDrainToLimitedCapacity() {
        DelayQueue<Integer> dq = new DelayQueue<>();
        dq.offer(1);
        dq.offer(2);
        dq.offer(3);
        Collection<Integer> c = new ArrayList<>();
        int limit = 2;
        int drainedCount = dq.drainTo(c, limit);
        assert drainedCount == limit : "drainTo should drain up to the specified limit";
        assert c.size() == limit : "Collection size should match limit";
        assert dq.size() == 1 : "Queue should have remaining elements";
    }
}
