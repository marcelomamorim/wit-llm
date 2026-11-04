/*
 * @test
 * @summary SNIHostName.createSNIMatcher rejects null and returns a matcher for a valid regex
 * @run main SNIHostNameWitupTest
 */

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.StandardConstants;

public class SNIHostNameWitupTest {

    public static void main(String[] args) throws Exception {
        try {
            SNIHostName.createSNIMatcher(null);
            throw new RuntimeException("NullPointerException expected for null regex");
        } catch (NullPointerException e) {
            // expected
        }

        SNIMatcher matcher = SNIHostName.createSNIMatcher("www\\.example\\.com");
        if (matcher == null) {
            throw new RuntimeException("createSNIMatcher returned null");
        }
        if (matcher.getType() != StandardConstants.SNI_HOST_NAME) {
            throw new RuntimeException("Unexpected matcher type: " + matcher.getType());
        }
        if (!matcher.matches(new SNIHostName("www.example.com"))) {
            throw new RuntimeException("Matcher should accept www.example.com");
        }
        if (matcher.matches(new SNIHostName("other.example.org"))) {
            throw new RuntimeException("Matcher should reject other.example.org");
        }
    }
}
