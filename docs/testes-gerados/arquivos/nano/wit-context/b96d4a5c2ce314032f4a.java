/* @test    @summary Test for URISyntaxException constructor with input and reason.    @run main URISyntaxExceptionWitupTest */

import java.net.URISyntaxException;

public class URISyntaxExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullInput();
        testConstructorWithNullReason();
        testConstructorWithValidInputs();
    }

    private static void testConstructorWithNullInput() {
        try {
            new URISyntaxException(null, "reason");
            System.out.println("FAIL: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null input");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null input: " + t);
        }
    }

    private static void testConstructorWithNullReason() {
        try {
            new URISyntaxException("input", null);
            System.out.println("FAIL: Expected NullPointerException for null reason");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null reason");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null reason: " + t);
        }
    }

    private static void testConstructorWithValidInputs() {
        try {
            URISyntaxException e = new URISyntaxException("inputString", "Invalid URI");
            if (e.getInput().equals("inputString")) {
                System.out.println("PASS: getInput() returns correct value");
            } else {
                System.out.println("FAIL: getInput() returns incorrect value");
            }
            if (e.getReason().equals("Invalid URI")) {
                System.out.println("PASS: getReason() returns correct value");
            } else {
                System.out.println("FAIL: getReason() returns incorrect value");
            }
            if (e.getIndex() == -1) {
                System.out.println("PASS: getIndex() returns -1 as expected");
            } else {
                System.out.println("FAIL: getIndex() returns incorrect value");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception during valid input test: " + t);
        }
    }
}
