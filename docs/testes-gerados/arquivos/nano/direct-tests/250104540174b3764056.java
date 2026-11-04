/* @test    @summary Test for RegularEnumSet.remove() method behavior    @run main RegularEnumSetWitupTest */

import java.util.*;

public class RegularEnumSetWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutPriorNext();
        testRemoveAfterNext();
        testRemoveWithoutNext();
    }

    static void testRemoveWithoutPriorNext() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A);
        RegularEnumSet<SampleEnum> regularSet = new RegularEnumSet<>(SampleEnum.class, SampleEnum.values());
        // Manually set internal state for testing
        // Since internal fields are private, simulate behavior via iterator
        Iterator<SampleEnum> it = set.iterator();
        // Remove without calling next() should throw IllegalStateException
        try {
            // No call to next()
            // Attempt to call remove() directly
            // Since remove() is not accessible directly, simulate via iterator
            // But in this test, we test the behavior of iterator's remove() without prior next()
            // So, create a custom iterator that calls remove() without next()
            // Alternatively, test the behavior of set.iterator().remove() without next()
            // which is not possible directly, so test the iterator's remove() after no next()
            // For simplicity, create an iterator and call remove() immediately
            it.remove();
            // Should throw IllegalStateException
            System.out.println("FAIL: Expected IllegalStateException not thrown in testRemoveWithoutPriorNext");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException in testRemoveWithoutPriorNext");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception in testRemoveWithoutPriorNext: " + e);
        }
    }

    static void testRemoveAfterNext() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A, SampleEnum.B);
        RegularEnumSet<SampleEnum> regularSet = new RegularEnumSet<>(SampleEnum.class, SampleEnum.values());
        Iterator<SampleEnum> it = set.iterator();
        try {
            if (it.hasNext()) {
                it.next();
                it.remove();
                // After removal, element should be removed from set
                if (!set.contains(SampleEnum.A) && set.contains(SampleEnum.B)) {
                    System.out.println("PASS: Element removed successfully in testRemoveAfterNext");
                } else {
                    System.out.println("FAIL: Element not removed as expected in testRemoveAfterNext");
                }
            } else {
                System.out.println("FAIL: Iterator has no next in testRemoveAfterNext");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception in testRemoveAfterNext: " + e);
        }
    }

    static void testRemoveWithoutNext() {
        EnumSet<SampleEnum> set = EnumSet.of(SampleEnum.A);
        RegularEnumSet<SampleEnum> regularSet = new RegularEnumSet<>(SampleEnum.class, SampleEnum.values());
        Iterator<SampleEnum> it = set.iterator();
        try {
            // Call remove() without next()
            it.remove();
            System.out.println("FAIL: Expected IllegalStateException not thrown in testRemoveWithoutNext");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException in testRemoveWithoutNext");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception in testRemoveWithoutNext: " + e);
        }
    }

    enum SampleEnum {
        A, B, C;
    }
}
