import java.util.LinkedHashMap;
import java.util.ArrayList;

/**
 * Prefix-Trie. Supports linear time find() and insert().
 * Should support determining whether a word is a full word in the
 * Trie or a prefix.
 * @author
 */
public class Trie {
    private final String alphabet;
    private int numInserted = 0;

    private class TrieNode {
        boolean isFullWord;
        LinkedHashMap<Character, TrieNode> links;

        private TrieNode() {
            this.isFullWord = isFullWord;
            this.links = new LinkedHashMap<Character, TrieNode>();

            if (alphabet != null) {
                for (int i = 0; i < alphabet.length(); i++) {
                    this.links.put(alphabet.charAt(i), null);
                }
            }
        }

        boolean hasCharacter(char c) {
            return links.containsKey(c);
        }

        TrieNode getChild(char c) {
            return links.get(c);
        }

        void addChild(char c, TrieNode n) {
            links.put(c, n);
        }
    }

    private TrieNode root;

    public Trie() {
        this.alphabet = null;
        root = new TrieNode();
    }

    public Trie(String alphabet) {
        this.alphabet = alphabet;
        root = new TrieNode();
    }

    public boolean find(String s, boolean isFullWord) {
        TrieNode curr = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!curr.hasCharacter(c)) {
                return false;
            }

            curr = curr.getChild(c);
        }

        return curr.isFullWord == isFullWord;
    }

    public void insert(String s) {
        numInserted++;
        insert(s, root, 0);
    }

    private TrieNode insert(String s, TrieNode n, int index) {
        if (n == null) {
            n = new TrieNode();
        }

        if (index == s.length()) {
            n.isFullWord = true;
            return n;
        }

        char c = s.charAt(index);
        n.addChild(c, insert(s, n.getChild(c), index+1));

        return n;
    }

    public ArrayList<String> getInsertedWords() {
        return getInsertedWords(root, "");
    }

    public ArrayList<String> getInsertedWords(TrieNode n, String soFar) {
        ArrayList<String> words = new ArrayList<String>();

        if (n == null || numInserted == 0) {
            return words;
        }

        for (char c : n.links.keySet()) {
            TrieNode node = n.links.get(c);
            if (node != null && node.isFullWord) {
                words.add(soFar + c);
            }

            for (String word : this.getInsertedWords(node, soFar + c)) {
                words.add(word);
            }
        }

        return result;
    }
}
