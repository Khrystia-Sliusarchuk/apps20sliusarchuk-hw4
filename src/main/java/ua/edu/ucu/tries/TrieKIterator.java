package ua.edu.ucu.tries;

import java.util.Iterator;

public class TrieKIterator implements Iterator<String> {
    private Iterator<String> iter;
    private int k;
    private String next;
    private int maxPossibleLength = 0;
    private int num = 0;


    public TrieKIterator(int k, Iterator<String> iter) {
        this.k = k;
        this.iter = iter;
        search();
    }

    @Override
    public boolean hasNext() {
        if (next == null) {
            return false;
        }
        return true;
    }

    @Override
    public String next() {
        String res = next;
        search();
        return res;
    }

    private void search() {
        if (iter.hasNext()) {
            next = iter.next();
            int tempLength = next.length();
            if (tempLength > maxPossibleLength && num == k) {
                next = null;
                return;
            }
            if (tempLength > maxPossibleLength) {
                maxPossibleLength = tempLength;
                num = num + 1;
            }
        } else {
            next = null;
            return;
        }
    }

    public static Iterable<String> wordIter(int k, Iterable<String> iter) {
        return () -> new TrieKIterator(k, iter.iterator());
    }
}

