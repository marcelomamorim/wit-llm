/* @test
   @summary Testa o método reset() de BufferedInputStream para comportamentos observáveis.
   @run main BufferedInputStreamWitupTest_Paa9464dee610
 */
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest_Paa9464dee610 {
    public static void main(String[] args) throws Exception {
        testResetWithNullBuffer();
        testResetWithInvalidMark();
    }

    private static void testResetWithNullBuffer() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1, 2, 3});
        BufferedInputStream bis = new BufferedInputStream(bais);
        // Forçar o buffer a ser null via reflexão não é possível sem acesso interno, então simular condição similar:
        // Como não há método público para setar buf=null, testar comportamento quando o stream está fechado.
        try {
            bis.close();
            bis.reset();
            throw new AssertionError("Esperado IOException ao reset após close");
        } catch (IOException e) {
            // esperado
        }
    }

    private static void testResetWithInvalidMark() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1, 2, 3});
        BufferedInputStream bis = new BufferedInputStream(bais);
        try {
            // Não marcar antes de reset, deve lançar IOException
            bis.reset();
            throw new AssertionError("Esperado IOException ao reset sem marca válida");
        } catch (IOException e) {
            // esperado
        }
    }
}
