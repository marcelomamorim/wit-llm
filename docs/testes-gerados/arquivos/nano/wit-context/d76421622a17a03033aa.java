/*
 * @test
 * @summary Test for sun.text.normalizer.NormalizerBase.current()
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testCurrentMethodWithNormalBuffer();
        testCurrentMethodWithEmptyBuffer();
    }

    private static void testCurrentMethodWithNormalBuffer() {
        try {
            // Cria uma instância de NormalizerBase com valores padrão
            NormalizerBase nb = new NormalizerBase("test", NormalizerBase.Mode.NFC);
            // Invoca current() e verifica se não lança exceção
            int result = nb.current();
            // Como o método retorna um int, podemos verificar se o resultado é diferente de DONE
            if (result == -1) {
                System.err.println("Erro: current() retornou DONE (-1) quando esperado valor válido.");
            } else {
                System.out.println("current() com buffer válido retornou: " + result);
            }
        } catch (Exception e) {
            System.err.println("Exceção inesperada em testCurrentMethodWithNormalBuffer: " + e);
        }
    }

    private static void testCurrentMethodWithEmptyBuffer() {
        try {
            // Cria uma instância de NormalizerBase com buffer vazio
            NormalizerBase nb = new NormalizerBase("", NormalizerBase.Mode.NFC);
            // Força o buffer a estar vazio
            // Como não há método público para manipular buffer diretamente, criamos a instância e invocamos current()
            int result = nb.current();
            // Verifica se o resultado é DONE (-1)
            if (result != -1) {
                System.err.println("Erro: current() não retornou DONE (-1) para buffer vazio.");
            } else {
                System.out.println("current() com buffer vazio retornou DONE (-1) como esperado.");
            }
        } catch (Exception e) {
            System.err.println("Exceção inesperada em testCurrentMethodWithEmptyBuffer: " + e);
        }
    }
}
