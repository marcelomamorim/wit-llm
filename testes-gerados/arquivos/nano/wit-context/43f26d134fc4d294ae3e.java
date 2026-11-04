/*
 * @test
 * @summary Test for sun.security.util.LocalizedMessage.formatLocalized
 * @run main LocalizedMessageWitupTest
 */

import sun.security.util.LocalizedMessage;

public class LocalizedMessageWitupTest {

    public static void main(String[] args) {
        testFormatLocalizedWithValidArguments();
        testFormatLocalizedWithNullArguments();
    }

    private static void testFormatLocalizedWithValidArguments() {
        LocalizedMessage msg = new LocalizedMessage("some.key");
        String result = msg.formatLocalized("arg1", "arg2");
        System.out.println("Test formatLocalized with valid arguments: " + result);
        // Observável: não há exceção, resultado não nulo esperado
    }

    private static void testFormatLocalizedWithNullArguments() {
        LocalizedMessage msg = new LocalizedMessage("some.key");
        String result = msg.formatLocalized((Object[]) null);
        System.out.println("Test formatLocalized with null arguments: " + result);
        // Observável: não há exceção, resultado esperado mesmo com null
    }
}
