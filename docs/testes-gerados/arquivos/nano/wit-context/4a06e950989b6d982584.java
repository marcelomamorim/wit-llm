/* @test    @summary Testa o construtor InvalidPathException(String input, String reason) com entradas nulas e válidas    @run main InvalidPathExceptionWitupTest */

import java.nio.file.InvalidPathException;

public class InvalidPathExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullInput();
        testConstructorWithNullReason();
        testConstructorWithValidInputs();
    }

    private static void testConstructorWithNullInput() {
        try {
            new InvalidPathException(null, "Reason");
            System.out.println("FAIL: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null input");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null input: " + t);
        }
    }

    private static void testConstructorWithNullReason() {
        try {
            new InvalidPathException("someInput", null);
            System.out.println("FAIL: Expected NullPointerException for null reason");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null reason");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null reason: " + t);
        }
    }

    private static void testConstructorWithValidInputs() {
        String input = "invalid/path";
        String reason = "Invalid characters";
        try {
            InvalidPathException e = new InvalidPathException(input, reason);
            if (e.getInput().equals(input) && e.getReason().equals(reason) && e.getIndex() == -1) {
                System.out.println("PASS: Valid inputs create exception with correct properties");
            } else {
                System.out.println("FAIL: Exception properties mismatch");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for valid inputs: " + t);
        }
    }
}
