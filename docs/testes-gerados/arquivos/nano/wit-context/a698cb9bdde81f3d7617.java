/* @test    @summary Test for OptionalDouble.getAsDouble() method to verify behavior when value is absent.    @run main OptionalDoubleWitupTest */

import java.util.OptionalDouble;

public class OptionalDoubleWitupTest {

    public static void main(String[] args) {
        testGetAsDoubleThrowsExceptionWhenEmpty();
        testGetAsDoubleReturnsValueWhenPresent();
    }

    private static void testGetAsDoubleThrowsExceptionWhenEmpty() {
        OptionalDouble emptyOpt = OptionalDouble.empty();
        try {
            emptyOpt.getAsDouble();
            System.out.println("FAIL: Expected NoSuchElementException was not thrown.");
        } catch (NoSuchElementException e) {
            System.out.println("PASS: NoSuchElementException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testGetAsDoubleReturnsValueWhenPresent() {
        OptionalDouble opt = OptionalDouble.of(3.14);
        double result = opt.getAsDouble();
        if (result == 3.14) {
            System.out.println("PASS: getAsDouble() returned the correct value.");
        } else {
            System.out.println("FAIL: getAsDouble() returned incorrect value: " + result);
        }
    }
}
