/*
 * @test
 * @summary Test for LongPipeline.accept(long) method.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testAcceptWithValidInput();
        testAcceptWithNullMapper();
    }

    private static void testAcceptWithValidInput() {
        try {
            // Cria um pipeline fictício com um downstream que captura o valor
            final boolean[] called = {false};
            LongPipeline downstream = new LongPipeline() {
                @Override
                public void accept(long t) {
                    called[0] = true;
                    // Verifica se o valor recebido é o esperado
                    if (t != 42L) {
                        throw new AssertionError("Valor inesperado: " + t);
                    }
                }
            };
            // Cria uma instância de LongPipeline com um mapper que retorna o mesmo valor
            LongPipeline pipeline = new LongPipeline() {
                @Override
                public void accept(long t) {
                    // Chama o método a ser testado
                    pipeline.accept(t);
                }
            };
            // Invoca o método accept com valor válido
            pipeline.accept(42L);
            // Verifica se o downstream foi chamado
            if (!called[0]) {
                throw new AssertionError("Downstream não foi chamado");
            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw new AssertionError("Teste falhou", t);
        }
    }

    private static void testAcceptWithNullMapper() {
        try {
            // Como o método aceita um long primitivo, não há como passar null diretamente
            // Teste de comportamento padrão: apenas chama accept com valor qualquer
            LongPipeline pipeline = new LongPipeline() {
                @Override
                public void accept(long t) {
                    // método vazio
                }
            };
            pipeline.accept(0L);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new AssertionError("Teste falhou", t);
        }
    }
}
