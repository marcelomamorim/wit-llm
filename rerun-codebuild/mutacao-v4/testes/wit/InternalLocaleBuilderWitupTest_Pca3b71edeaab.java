/* @test
   @summary Testa o método addUnicodeLocaleAttribute para cobrir exceções de validação.
   @run main InternalLocaleBuilderWitupTest_Pca3b71edeaab
 */
import java.util.Objects;
public class InternalLocaleBuilderWitupTest_Pca3b71edeaab {
    public static void main(String[] args) throws Exception {
        testAddUnicodeLocaleAttributeInvalid();
        testAddUnicodeLocaleAttributeValid();
    }

    private static void testAddUnicodeLocaleAttributeInvalid() {
        sun.util.locale.InternalLocaleBuilder builder = new sun.util.locale.InternalLocaleBuilder();
        String[] invalidAttributes = {"", "a", "abc", "abcdefgh", "ab", "aBc"};
        for (String attr : invalidAttributes) {
            try {
                builder.addUnicodeLocaleAttribute(attr);
                throw new AssertionError("Expected LocaleSyntaxException for attribute: " + attr);
            } catch (java.util.LocaleSyntaxException e) {
                // expected
            }
        }
    }

    private static void testAddUnicodeLocaleAttributeValid() throws Exception {
        sun.util.locale.InternalLocaleBuilder builder = new sun.util.locale.InternalLocaleBuilder();
        String[] validAttributes = {"abc", "a1b2", "abc123", "A_B"};
        for (String attr : validAttributes) {
            builder.addUnicodeLocaleAttribute(attr);
        }
        // Optional: verificar se atributos foram adicionados corretamente
        // Como uattributes é privado, não podemos acessá-lo diretamente; assumindo que o método funciona se não lançar exceções
    }
}
