import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Label decryptedMsgLabel;

    @FXML
    private TextField encMsgTF;

    @FXML
    private Label encyptedMsgLabel;

    @FXML
    private TextField key2TF;

    @FXML
    private TextField keyTF;

    @FXML
    private TextField msgTF;

    private static final String ROMANIAN_ALPHABET = "AĂÂBCDEFGHIÎJKLMNOPQRSȘTȚUVWXYZ";

    @FXML
    void decryptBtn(ActionEvent event) {
        String encryptedText = encMsgTF.getText();
        String key = key2TF.getText();

        String decryptedText = decrypt(encryptedText, key);
        
        decryptedMsgLabel.setText(decryptedText);
    }

    @FXML
    void encryptBtn(ActionEvent event) {
        String text = msgTF.getText();
        String key = keyTF.getText();

        String encryptedText = encrypt(text, key);
        
        encyptedMsgLabel.setText(encryptedText);

        }

    @FXML
    void decryptBtnWithoutKey(ActionEvent event) {
        String encryptedText = encMsgTF.getText();
        String key = keyTF.getText();

        String decryptedText = decrypt(encryptedText, key);

        decryptedMsgLabel.setText(decryptedText);

        }

    String encrypt(String text, String key) {
        StringBuilder encryptedText = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();
        int keyIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (ROMANIAN_ALPHABET.indexOf(c) != -1) {
                int shift = ROMANIAN_ALPHABET.indexOf(key.charAt(keyIndex));
                int index = (ROMANIAN_ALPHABET.indexOf(c) + shift) % ROMANIAN_ALPHABET.length();
                encryptedText.append(ROMANIAN_ALPHABET.charAt(index));
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                encryptedText.append(c);
            }
        }
        System.out.print(encryptedText.toString());
        return encryptedText.toString();
    }

    String decrypt(String text, String key) {
        StringBuilder decryptedText = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();
        int keyIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (ROMANIAN_ALPHABET.indexOf(c) != -1) {
                int shift = ROMANIAN_ALPHABET.indexOf(key.charAt(keyIndex));
                int index = (ROMANIAN_ALPHABET.indexOf(c) - shift + ROMANIAN_ALPHABET.length()) % ROMANIAN_ALPHABET.length();
                decryptedText.append(ROMANIAN_ALPHABET.charAt(index));
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                decryptedText.append(c);
            }
        }
        System.out.print(decryptedText.toString());
        return decryptedText.toString();
    }

public static String decryptWithoutKey(String ciphertext) {
    List<Integer> distances = new ArrayList<>();
    for (int i = 0; i < ciphertext.length() - 3; i++) {
        String substring = ciphertext.substring(i, i + 3);
        int nextIndex = ciphertext.indexOf(substring, i + 3);
        while (nextIndex != -1) {
            distances.add(nextIndex - i);
            nextIndex = ciphertext.indexOf(substring, nextIndex + 1);
        }
    }
    List<Integer> factors = new ArrayList<>();
    for (int i = 0; i < distances.size(); i++) {
        for (int j = i + 1; j < distances.size(); j++) {
            int factor = gcd(distances.get(i), distances.get(j));
            if (factor > 1) {
                factors.add(factor);
            }
        }
    }
    Map<Integer, Integer> factorCounts = new HashMap<>();
    for (int factor : factors) {
        if (!factorCounts.containsKey(factor)) {
            factorCounts.put(factor, 0);
        }
        factorCounts.put(factor, factorCounts.get(factor) + 1);
    }
    int maxCount = 0;
    int mostCommonFactor = -1;
    for (Map.Entry<Integer, Integer> entry : factorCounts.entrySet()) {
        if (entry.getValue() > maxCount) {
            maxCount = entry.getValue();
            mostCommonFactor = entry.getKey();
        }
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < mostCommonFactor; i++) {
        StringBuilder currentSubstring = new StringBuilder();
        for (int j = i; j < ciphertext.length(); j += mostCommonFactor) {
            currentSubstring.append(ciphertext.charAt(j));
        }
        sb.append(getMostFrequentCharacter(currentSubstring.toString()));
    }
    return sb.toString();
}

private static int gcd(int a, int b) {
    if (b == 0) {
        return a;
    } else {
        return gcd(b, a % b);
    }
}

private static char getMostFrequentCharacter(String s) {
    Map<Character, Integer> characterCounts = new HashMap<>();
    for (char c : s.toCharArray()) {
        if (!characterCounts.containsKey(c)) {
            characterCounts.put(c, 0);
        }
        characterCounts.put(c, characterCounts.get(c) + 1);
    }
    char mostFrequentCharacter = ' ';
    int maxCount = 0;
    for (Map.Entry<Character, Integer> entry : characterCounts.entrySet()) {
        if (entry.getValue() > maxCount) {
            maxCount = entry.getValue();
            mostFrequentCharacter = entry.getKey();
        }
    }
    return mostFrequentCharacter;
}

}