package com.example;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class UndirectedGraph<V> implements Iterable<V> {
    final Map<V, List<Edge<V>>> vertices = new HashMap<>();

    private final BiFunction<V, V, Edge<V>> edgeSupplier;
    private final ReentrantReadWriteLock lock;

    public UndirectedGraph() {
        this(UndirectedEdge::new);
    }

    public UndirectedGraph(BiFunction<V, V, Edge<V>> edgeSupplier) {
        this.edgeSupplier = edgeSupplier;
        this.lock = new ReentrantReadWriteLock();
    }

    protected void addEdgeInternal(V start, V end, Edge<V> edge) {
        vertices.get(start).add(edge);
        vertices.get(end).add(edge);
    }

    public final void addVertex(V value) {
        lock.writeLock().lock();
        try {
            vertices.computeIfAbsent(value, k -> new ArrayList<>());
        } finally {
            lock.writeLock().unlock();
        }
    }

    public final Edge addEdge(V start, V end) {
        lock.writeLock().lock();

        try {
            if (!vertices.containsKey(start) || !vertices.containsKey(end)) {
                throw new IllegalArgumentException("Vertex doesn't exist in graph");
            }

            Edge<V> edge = edgeSupplier.apply(start, end);

            addEdgeInternal(start, end, edge);

            return edge;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Edge<V>> getPath(V start, V end) {
        lock.readLock().lock();
        try {

            if (start.equals(end)) {
                return Collections.emptyList();
            }


            List<Edge<V>> result = getSubpath(start, end, new HashSet<>(), new LinkedList<>());

            if (result.isEmpty()) {
                return null;
            }

            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    List<Edge<V>> getSubpath(V start, V end, Set<V> visited, List<Edge<V>> path) {
        if (visited.contains(start)) {
            return Collections.emptyList();
        }

        visited.add(start);

        List<Edge<V>> edges = vertices.get(start);

        if (edges.isEmpty()) {
            return Collections.emptyList();
        }

        for (Edge<V> edge : edges) {
            List<Edge<V>> subPath = new LinkedList<>(path);
            subPath.add(edge);

            V edgeEnd = edge.end(start);

            if (edgeEnd.equals(end)) {
                return subPath;
            }

            List<Edge<V>> nextPath = getSubpath(edgeEnd, end, visited, subPath);

            if (!nextPath.isEmpty()) {
                return nextPath;
            }
        }

        return Collections.emptyList();
    }

    @Override
    public Iterator<V> iterator() {
        return this.vertices.keySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super V> action) {
        this.vertices.keySet().forEach(action);
    }

    @Override
    public Spliterator<V> spliterator() {
        return this.vertices.keySet().spliterator();
    }
}
