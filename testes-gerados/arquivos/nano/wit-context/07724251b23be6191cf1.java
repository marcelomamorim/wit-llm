/*
 * @test
 * @summary Testa o método isSupported de java.nio.charset.Charset
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;

public class CharsetWitupTest {

    public static void main(String[] args) {
        testIsSupportedWithValidCharset();
        testIsSupportedWithNull();
        testIsSupportedWithInvalidCharset();
    }

    private static void testIsSupportedWithValidCharset() {
        String charsetName = "UTF-8";
        boolean result = Charset.isSupported(charsetName);
        System.out.println("isSupported(\"UTF-8\") = " + result);
        // Espera-se true, pois UTF-8 é suportado na maioria das JVMs.
    }

    private static void testIsSupportedWithNull() {
        try {
            boolean result = Charset.isSupported(null);
            System.out.println("isSupported(null) = " + result);
            // Espera-se que lance IllegalArgumentException, mas como o método atual não lança,
            // o resultado será false, pois lookup(null) retornará null.
        } catch (NullPointerException e) {
            System.out.println("isSupported(null) threw NullPointerException");
        }
    }

    private static void testIsSupportedWithInvalidCharset() {
        String invalidCharset = "InvalidCharsetName";
        boolean result = Charset.isSupported(invalidCharset);
        System.out.println("isSupported(\"InvalidCharsetName\") = " + result);
        // Espera-se false, pois charset inválido não é suportado.
    }
}
