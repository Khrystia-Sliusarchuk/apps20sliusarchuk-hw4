package ua.edu.ucu.tries;

import java.util.Collections;

public class RWayTrie implements Trie {
    private static int R = 256;
    private Node root = new Node();
    private int length;

    public RWayTrie() {
        this.length = 0;
    }

    public static class Node {
        private int value;
        private Node[] next;

        public Node() {
            this.value = -1;
            this.next = new Node[R];
        }

        public int getValue() {
            return value;
        }

        public Node setValue(int value) {
            this.value = value;
            return this;
        }

        public Node[] getNext() {
            return next;
        }


    }

    @Override
    public void add(Tuple t) {
        this.root = add(this.root, t.term, t.weight, 0);
        length += 1;
    }

    private Node add(Node node, String term, int weight, int ind) {
        if (node == null) {
            node = new Node();
        }
        if (ind == term.length()) {
            node.setValue(weight);
            return node;
        }
        char c = term.charAt(ind);
        node.next[c] = add(node.next[c], term, weight, ind + 1);
        return node;
    }

    public int get(String word) {
        Node node = get(this.root, word, 0);
        if (node == null) {
            return -1;
        }
        return node.getValue();
    }

    private Node get(Node node, String term, int ind) {
        if (node == null) {
            return null;
        }
        if (ind == term.length()) {
            return node;
        }
        char c = term.charAt(ind);
        return get(node.next[c], term, ind + 1);
    }

    @Override
    public boolean contains(String word) {
        if (word == null || get(word) == -1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(String word) {
        if (word == null || !contains(word)) {
            return false;
        } else {
            this.root = delete(this.root, word, 0);
            length -= 1;
            return true;
        }
    }

    private Node delete(Node node, String key, int ind) {
        if (node == null) {
            return null;
        }
        if (ind == key.length())
            node.setValue(-1);
        else {
            char c = key.charAt(ind);
            node.next[c] = delete(node.next[c], key, ind + 1);
        }
        if (node.getValue() != -1) {
            return node;
        }
        for (char c = 0; c < R; c++)
            if (node.next[c] != null) {
                return node;
            }
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Node temp = get(this.root, s, 0);
        if (temp != null) {
            return TrieIterator.wordIter(s, temp);
        }
        return Collections::emptyIterator;

    }

    @Override
    public int size() {
        return length;
    }


}
