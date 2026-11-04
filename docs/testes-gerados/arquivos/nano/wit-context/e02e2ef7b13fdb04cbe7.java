/* @test    @summary Test for Optional.get() method to verify exception when value is absent    @run main OptionalWitupTest */

import java.util.Optional;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testGetThrowsNoSuchElementExceptionWhenEmpty();
        testGetReturnsValueWhenPresent();
    }

    private static void testGetThrowsNoSuchElementExceptionWhenEmpty() {
        Optional<Object> emptyOpt = Optional.empty();
        try {
            emptyOpt.get();
            System.out.println("FAIL: Expected NoSuchElementException was not thrown.");
        } catch (NoSuchElementException e) {
            System.out.println("PASS: NoSuchElementException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testGetReturnsValueWhenPresent() {
        String value = "test";
        Optional<String> opt = Optional.of(value);
        String result = null;
        try {
            result = opt.get();
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception thrown: " + t);
            return;
        }
        if (value.equals(result)) {
            System.out.println("PASS: get() returned the correct value.");
        } else {
            System.out.println("FAIL: get() did not return the expected value.");
        }
    }
}
