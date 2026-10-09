/* @test
   @summary Testa o método start(String name) da classe java.util.regex.Matcher.
   @run main MatcherWitupTest_P2b93625c5015
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MatcherWitupTest_P2b93625c5015 {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        // Teste com nome válido, que existe no grupo nomeado
        try {
            int startIndex = matcher.start("a");
            System.out.println("start("a") = " + startIndex);
        } catch (Exception e) {
            System.out.println("Erro ao chamar start("a"): " + e);
        }
        // Teste com nome nulo, deve lançar NullPointerException
        try {
            matcher.start(null);
            System.out.println("Erro: esperado NullPointerException ao passar null");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException esperado ao passar null");
        } catch (Exception e) {
            System.out.println("Erro inesperado ao passar null: " + e);
        }
        // Teste com nome não existente, deve lançar IllegalArgumentException
        try {
            matcher.start("nonexistent");
            System.out.println("Erro: esperado IllegalArgumentException ao nome não existente");
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException esperado ao nome não existente");
        } catch (Exception e) {
            System.out.println("Erro inesperado ao nome não existente: " + e);
        }
    }
}
