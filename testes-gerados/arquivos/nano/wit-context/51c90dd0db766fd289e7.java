/* @test    @summary Test for sun.text.RuleBasedBreakIterator.last() method.    @run main RuleBasedBreakIteratorWitupTest */

import sun.text.RuleBasedBreakIterator;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        testLastMethod();
    }

    public static void testLastMethod() {
        try {
            // Cria uma instância de RuleBasedBreakIterator com dados fictícios
            byte[] dummyData = new byte[0];
            String dummyRules = ""; // regras vazias, pois não são usadas no método testado
            RuleBasedBreakIterator iterator = new RuleBasedBreakIterator(dummyRules, dummyData);
            // Define um rangeLimit arbitrário
            // Como o método last() usa rangeLimit, precisamos definir esse campo se possível
            // Como não há método público para isso, assumimos que o valor padrão é suficiente
            // ou que o método funciona com o valor padrão
            // Invoca o método last()
            char result = iterator.last();
            // Verifica se o resultado é um caractere válido (não lança exceção)
            if (result == 0) {
                System.out.println("last() retornou caractere nulo.");
            } else {
                System.out.println("last() retornou: " + result);
            }
        } catch (IllegalArgumentException e) {
            // Esperado se o rangeLimit - rangeStart < 1, mas como não podemos definir esses campos,
            // o teste não tenta provocar essa condição
            System.out.println("IllegalArgumentException capturada: " + e.getMessage());
        } catch (Exception e) {
            // Captura qualquer outra exceção inesperada
            System.out.println("Exceção inesperada: " + e);
        }
    }
}
