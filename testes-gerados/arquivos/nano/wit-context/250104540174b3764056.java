/* @test    @summary Test for RegularEnumSet.remove() method to verify IllegalStateException is thrown when lastReturned is 0    @run main RegularEnumSetWitupTest */

import java.util.RegularEnumSet;

public class RegularEnumSetWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsExceptionWhenLastReturnedIsZero();
    }

    private static void testRemoveThrowsExceptionWhenLastReturnedIsZero() {
        RegularEnumSet<SampleEnum> set = new RegularEnumSet<>(SampleEnum.class, SampleEnum.values());
        // Create an iterator and do not call next(), so lastReturned remains 0
        RegularEnumSet<SampleEnum>.EnumSetIterator iterator = set.new EnumSetIterator();
        // Ensure lastReturned is 0
        if (iterator.lastReturned != 0) {
            throw new AssertionError("Expected lastReturned to be 0 initially");
        }
        boolean exceptionThrown = false;
        try {
            iterator.remove();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException to be thrown when lastReturned is 0");
        }
    }

    private enum SampleEnum {
        A, B, C
    }
}
