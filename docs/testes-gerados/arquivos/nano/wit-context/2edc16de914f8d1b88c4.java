/* @test    @summary Test for X500Principal constructor with null name and keywordMap    @run main X500PrincipalWitupTest */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest {

    public static void main(String[] args) {
        testConstructorNullName();
        testConstructorNullKeywordMap();
    }

    private static void testConstructorNullName() {
        try {
            new X500Principal(null, new HashMap<String, String>());
            System.err.println("Failed: Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type for null name: " + t);
        }
    }

    private static void testConstructorNullKeywordMap() {
        try {
            new X500Principal("CN=Duke", null);
            System.err.println("Failed: Expected NullPointerException for null keywordMap");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type for null keywordMap: " + t);
        }
    }
}
