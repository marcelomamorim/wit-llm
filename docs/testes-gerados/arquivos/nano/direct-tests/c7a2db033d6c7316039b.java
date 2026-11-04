/* @test    @summary Test URISyntaxException constructors and getters    @run main URISyntaxExceptionWitupTest */

import java.net.URISyntaxException;

public class URISyntaxExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorAndGetters();
    }

    static void testConstructorAndGetters() {
        String input = "http://example.com";
        String reason = "Invalid URI";
        int index = 5;
        URISyntaxException ex = new URISyntaxException(input, reason, index);
        assert ex.getInput().equals(input) : "getInput() failed";
        assert ex.getReason().equals(reason) : "getReason() failed";
        assert ex.getIndex() == index : "getIndex() failed";

        URISyntaxException ex2 = new URISyntaxException(input, reason);
        assert ex2.getInput().equals(input) : "getInput() failed in second constructor";
        assert ex2.getReason().equals(reason) : "getReason() failed in second constructor";
        assert ex2.getIndex() == -1 : "Default index should be -1";
    }
}
