package ua.edu.ucu.tries;

import org.javatuples.Pair;
import ua.edu.ucu.immutable.Queue;

import java.util.Iterator;

public class TrieIterator implements Iterator<String> {
    private Queue queue;
    private String next;
    private Pair pair;

    public TrieIterator(String s, RWayTrie.Node node) {
        this.queue = new Queue();
        this.pair = new Pair<String, RWayTrie.Node>(s, node);
        this.queue.enqueue(pair);
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
        while (!this.queue.empty()) {
            Pair tempPair = (Pair) this.queue.dequeue();
            String prefix = (String) tempPair.getValue0();
            RWayTrie.Node tempNode = (RWayTrie.Node) tempPair.getValue1();

            for (int c = 0; c < RWayTrie.R; c++) {
                Pair toAdd = new Pair<String, RWayTrie.Node>(prefix + (char) c,
                        tempNode.getNext()[c]);
                if (tempNode.getNext()[c] != null) {
                    this.queue.enqueue(toAdd);
                }
            }

            if (tempNode.getValue() != -1) {
                next = prefix;
                return;
            }

        }
        next = null;

    }

    public static Iterable<String> wordIter(String s, RWayTrie.Node node) {
        return () -> new TrieIterator(s, node);
    }
}
