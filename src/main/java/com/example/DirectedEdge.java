package com.example;

public class DirectedEdge<V> extends Edge<V> {

    public DirectedEdge(V start, V end) {
        super(start, end);
    }

    @Override
    public V end(V start) {
        return this.end;
    }
}
