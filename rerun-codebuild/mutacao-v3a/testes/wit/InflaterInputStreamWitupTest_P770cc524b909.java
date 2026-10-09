/*
 * @test
 * @summary Test for reset method in InflaterInputStream
 * @run main InflaterInputStreamWitupTest_P770cc524b909
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest_P770cc524b909 {

    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        try {
            byte[] data = {0};
            ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
            Inflater inflater = new Inflater();
            InflaterInputStream iis = new InflaterInputStream(byteIn, inflater);
            // Invoca o método que, de acordo com o método atual, deve lançar uma IOException
            iis.reset();
            System.out.println("Erro: reset() não lançou IOException como esperado.");
        } catch (IOException e) {
            if ("mark/reset not supported".equals(e.getMessage())) {
                System.out.println("Passou: reset() lançou IOException esperado.");
            } else {
                System.out.println("Falha: reset() lançou IOException com mensagem inesperada: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e);
        }
    }
}
