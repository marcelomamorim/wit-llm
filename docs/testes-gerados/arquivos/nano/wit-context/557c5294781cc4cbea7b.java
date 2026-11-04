/* @test    @summary Test for LinkedList.remove() method to verify behavior when list is empty    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveOnEmptyList();
    }

    public static void testRemoveOnEmptyList() {
        LinkedList<Object> list = new LinkedList<>();
        Object result = null;
        try {
            result = list.remove();
            // Se o método não lançar exceção, o resultado deve ser null ou uma exceção inesperada
            if (result != null) {
                throw new AssertionError("Expected null or exception, but got: " + result);
            }
        } catch (java.util.NoSuchElementException e) {
            // Exceção esperada quando a lista está vazia
        }
    }
}
