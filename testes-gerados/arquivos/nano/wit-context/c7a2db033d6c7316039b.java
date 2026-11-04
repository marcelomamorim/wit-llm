/* @test    @summary Test for URISyntaxException constructor with input, reason, and index    @run main URISyntaxExceptionWitupTest */

import java.net.URISyntaxException;

public class URISyntaxExceptionWitupTest {

    public static void main(String[] args) {
        testNullInput();
        testNullReason();
        testNegativeIndex();
        testValidConstruction();
    }

    private static void testNullInput() {
        try {
            new URISyntaxException(null, "reason", 0);
            System.out.println("FAIL: Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            System.out.println("PASS: NullPointerException thrown for null input");
        }
    }

    private static void testNullReason() {
        try {
            new URISyntaxException("input", null, 0);
            System.out.println("FAIL: Expected NullPointerException for null reason");
        } catch (NullPointerException expected) {
            System.out.println("PASS: NullPointerException thrown for null reason");
        }
    }

    private static void testNegativeIndex() {
        try {
            new URISyntaxException("input", "reason", -2);
            System.out.println("FAIL: Expected IllegalArgumentException for negative index less than -1");
        } catch (IllegalArgumentException expected) {
            System.out.println("PASS: IllegalArgumentException thrown for index < -1");
        }
    }

    private static void testValidConstruction() {
        String input = "http://example.com";
        String reason = "Invalid URI";
        int index = 5;
        URISyntaxException e = new URISyntaxException(input, reason, index);
        if (e.getInput().equals(input) && e.getReason().equals(reason) && e.getIndex() == index) {
            System.out.println("PASS: Valid construction with correct fields");
        } else {
            System.out.println("FAIL: Fields do not match after construction");
        }
    }
}
