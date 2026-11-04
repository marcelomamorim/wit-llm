/* @test    @summary Test ChoiceCallback.setSelectedIndexes behavior    @run main ChoiceCallbackWitupTest */

import javax.security.auth.callback.ChoiceCallback;

public class ChoiceCallbackWitupTest {

    public static void main(String[] args) {
        testSetSelectedIndexesAllowed();
        testSetSelectedIndexesDisallowed();
    }

    private static void testSetSelectedIndexesAllowed() {
        String prompt = "Select options";
        String[] choices = {"Option1", "Option2", "Option3"};
        int defaultChoice = 1;
        boolean multipleSelectionsAllowed = true;
        ChoiceCallback callback = new ChoiceCallback(prompt, choices, defaultChoice, multipleSelectionsAllowed);
        int[] selections = {0, 2};
        callback.setSelectedIndexes(selections);
        // Verify that selections are set correctly
        if (callback.selections == null || callback.selections.length != selections.length) {
            throw new AssertionError("Selections not set correctly");
        }
        for (int i = 0; i < selections.length; i++) {
            if (callback.selections[i] != selections[i]) {
                throw new AssertionError("Selection mismatch at index " + i);
            }
        }
    }

    private static void testSetSelectedIndexesDisallowed() {
        String prompt = "Select options";
        String[] choices = {"Option1", "Option2"};
        int defaultChoice = 0;
        boolean multipleSelectionsAllowed = false;
        ChoiceCallback callback = new ChoiceCallback(prompt, choices, defaultChoice, multipleSelectionsAllowed);
        int[] selections = {0};
        boolean exceptionThrown = false;
        try {
            callback.setSelectedIndexes(selections);
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
