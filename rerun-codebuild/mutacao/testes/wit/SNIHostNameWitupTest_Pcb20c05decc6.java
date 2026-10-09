/* @test    @summary Test for createSNIMatcher method in SNIHostName class    @run main SNIHostNameWitupTest_Pcb20c05decc6 */

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;

public class SNIHostNameWitupTest_Pcb20c05decc6 {

    public static void main(String[] args) {
        testCreateSNIMatcher_NullRegex();
        testCreateSNIMatcher_InvalidPattern();
    }

    private static void testCreateSNIMatcher_NullRegex() {
        try {
            SNIHostName.createSNIMatcher(null);
            System.err.println("Failed: Expected NullPointerException for null regex");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null regex");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testCreateSNIMatcher_InvalidPattern() {
        String invalidPattern = "["; // invalid regex
        try {
            SNIHostName.createSNIMatcher(invalidPattern);
            System.err.println("Failed: Expected IllegalArgumentException for invalid pattern");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown as expected for invalid pattern");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }
}
