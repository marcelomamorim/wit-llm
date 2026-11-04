/* @test    @summary Test URISyntaxException constructor with input and reason    @run main URISyntaxExceptionWitupTest */

import java.net.URISyntaxException;

public class URISyntaxExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithInputAndReason();
        testConstructorWithNullInput();
        testConstructorWithNullReason();
        testConstructorWithEmptyStrings();
    }

    static void testConstructorWithInputAndReason() {
        String input = "http://example.com";
        String reason = "Invalid URI";
        URISyntaxException e = new URISyntaxException(input, reason);
        assert e.getInput().equals(input) : "Input mismatch";
        assert e.getReason().equals(reason) : "Reason mismatch";
        assert e.getIndex() == -1 : "Index should be -1";
        assert e.getMessage().equals(reason) : "Message should be reason";
    }

    static void testConstructorWithNullInput() {
        try {
            new URISyntaxException(null, "Reason");
            assert false : "Expected NullPointerException";
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testConstructorWithNullReason() {
        try {
            new URISyntaxException("input", null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testConstructorWithEmptyStrings() {
        String input = "";
        String reason = "";
        URISyntaxException e = new URISyntaxException(input, reason);
        assert e.getInput().equals(input) : "Empty input mismatch";
        assert e.getReason().equals(reason) : "Empty reason mismatch";
        assert e.getIndex() == -1 : "Index should be -1";
        assert e.getMessage().equals(reason) : "Message should be reason";
    }
}
