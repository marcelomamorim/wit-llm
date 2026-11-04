/*
 * @test
 * @summary Testa o construtor ByteArrayOutputStream(int) com entradas inválidas.
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) {
        testNegativeSize();
        testZeroSize();
        testPositiveSize();
    }

    private static void testNegativeSize() {
        try {
            new ByteArrayOutputStream(-1);
            System.err.println("Falha: esperado IllegalArgumentException para tamanho negativo");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: exceção capturada para tamanho negativo");
        }
    }

    private static void testZeroSize() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
            if (baos != null && baos.buf != null && baos.buf.length == 0) {
                System.out.println("Sucesso: ByteArrayOutputStream com tamanho zero criado");
            } else {
                System.err.println("Falha: ByteArrayOutputStream com tamanho zero não criado corretamente");
            }
        } catch (Exception e) {
            System.err.println("Falha: exceção inesperada para tamanho zero: " + e);
        }
    }

    private static void testPositiveSize() {
        try {
            int size = 10;
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
            if (baos != null && baos.buf != null && baos.buf.length == size) {
                System.out.println("Sucesso: ByteArrayOutputStream com tamanho positivo criado");
            } else {
                System.err.println("Falha: ByteArrayOutputStream com tamanho positivo não criado corretamente");
            }
        } catch (Exception e) {
            System.err.println("Falha: exceção inesperada para tamanho positivo: " + e);
        }
    }
}
