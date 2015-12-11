import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;

public class AlphabetSort {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        if (!in.hasNext()) {
            throw new IllegalArgumentException("no input");
        }
        String alphabet = in.next();

        if (alphabet.equals("")) {
            throw new IllegalArgumentException("no input");
        }

        checkDuplicates(alphabet);

        Trie t = new Trie(alphabet);

        while (in.hasNext()) {
            String word = in.next();
            t.insert(word);
        }

        for (String word : t.getInsertedWords()) {
            System.out.println(word);
        }
    }

    public static void checkDuplicates(String alphabet) {
        HashSet<Character> seenValues = new HashSet<Character>();
        for (int i = 0; i < alphabet.length(); i++) {
            char c = alphabet.charAt(i);
            if(seenValues.contains(c)) {
                throw new IllegalArgumentException("duplicate character in alphabet");
            }
        }
    }
}
