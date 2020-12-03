package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.TrieKIterator;
import ua.edu.ucu.tries.Tuple;

import java.util.Collections;

/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;
    private static int L = 2;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int count = 0;
        if (strings == null) {
            return 0;
        }
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] == null) {
                continue;
            }
            for (String word : strings[i].split(" ")) {
                int wordLength = word.length();
                if (wordLength > L && !this.trie.contains(word)) {
                    Tuple toAdd = new Tuple(word, wordLength);
                    this.trie.add(toAdd);
                    count = count + 1;
                }
            }

        }
        return count;
    }


    public boolean contains(String word) {
        return this.trie.contains(word);
    }

    public boolean delete(String word) {
        return this.trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() < 2) {
            throw new IndexOutOfBoundsException("The length of prefix should be more than 2!");
        }
        return this.trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < 2) {
            throw new IndexOutOfBoundsException("The length of prefix should be more than 2!");
        }
        if (k < 0) {
            throw new IndexOutOfBoundsException("The length of words you want to receive should be more than 0!");
        }
        if (k == 1) {
            return Collections::emptyIterator;
        }
        return TrieKIterator.wordIter(k, this.trie.wordsWithPrefix(pref));
    }

    public int size() {
        return this.trie.size();
    }
}


