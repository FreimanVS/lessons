package com.vfreiman.lessons._1_algorithms_and_data_structures;

public class DisjointSet {
    public int[] parent;
    public int[] rank;

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];
    }

    public int find(int i) {
        if (i != parent[i]) {
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }

    int max = 0;
    public void max() {
        for (int i = 1; i < rank.length; i++) {
            if (rank[i] > max) {
                max = rank[i];
            }
        }
    }

    public void union(int i, int j) {
        int i_id = find(i);
        int j_id = find(j);
        if (i_id == j_id) {
            return;
        } else {
            parent[i_id] = j_id;
            rank[j_id] += rank[i_id];
            if ( rank[j_id] > max) {
                max = rank[j_id];
            }
        }
    }

    public void makeSet(int i, int rankValue) {
        parent[i] = i;
        rank[i] = rankValue;
    }
}
