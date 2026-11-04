/*
 * @test
 * @summary Test for Arrays.forEach(Consumer<? super E> action) method.
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.Consumer;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        int[] array = {1, 2, 3};
        // Como o método espera um Consumer, e o WIT indica que uma NullPointerException ocorre se action == null,
        // devemos testar esse caso.
        try {
            // Como Arrays.forEach é um método de instância, e o método fornecido é um array primitivo,
            // é necessário criar um método auxiliar que simule o comportamento esperado.
            // No entanto, o método original é de java.util.Arrays, que não possui método forEach para arrays primitivos.
            // Portanto, para fins de teste, criamos um método auxiliar que simula o comportamento.
            // Como o método original não é acessível diretamente, e o método de interesse é o método de array de objetos,
            // vamos criar um array de objetos para testar.
            String[] strArray = {"a", "b", "c"};
            // Invoca o método com null, esperando NullPointerException.
            Arrays.forEach(strArray, null);
            // Se não lançar exceção, o teste falha.
            System.err.println("Failed: Expected NullPointerException for null action.");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null action.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testForEachWithValidAction() {
        String[] array = {"a", "b", "c"};
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        Arrays.forEach(array, consumer);
        String result = sb.toString();
        if ("abc".equals(result)) {
            System.out.println("Passed: forEach applied consumer correctly.");
        } else {
            System.err.println("Failed: Unexpected result: " + result);
        }
    }
}
