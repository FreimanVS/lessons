package com.andersen.vfreiman.lessons._1_algorithms_and_data_structures;

import java.util.*;

public class DijkstrasAlgorithm {

    // the graph
    private static final Map<String, Map<String, Double>> graph = new HashMap<String, Map<String, Double>>() {
        {
            put("start", new HashMap<String, Double>() {
                {
                    put("a", 6.0);
                    put("b", 2.0);
                }
            });

            put("a", new HashMap<String, Double>() {
                {
                    put("fin", 1.0);
                }
            });

            put("b", new HashMap<String, Double>() {
                {
                    put("a", 3.0);
                    put("fin", 5.0);
                }
            });

            put("fin", new HashMap<>());
        }
    };

    // The costs table
    private static final Map<String, Double> costs = new HashMap<String, Double>() {
        {
            put("a", 6.0);
            put("b", 2.0);
            put("fin", Double.POSITIVE_INFINITY);
        }
    };

    // the parents table
    private static final Map<String, String> parents = new HashMap<String, String>() {
        {
            put("a", "start");
            put("b", "start");
            put("fin", null);
        }
    };

    private static final List<String> processed = new ArrayList<>();

    /**
     * It finds the lowest price which is not processed yet
     *
     * @return lowestCostNode
     */
    private static String findLowestCostNode() {
        Double lowestCost = Double.POSITIVE_INFINITY;
        String lowestCostNode = null;

        // Go through each node
        for (Map.Entry<String, Double> node : DijkstrasAlgorithm.costs.entrySet()) {
            Double cost = node.getValue();
            // If it's the lowest cost so far and hasn't been processed yet...
            if (cost < lowestCost && !processed.contains(node.getKey())) {
                // ... set it as the new lowest-cost node.
                lowestCost = cost;
                lowestCostNode = node.getKey();
            }
        }

        return lowestCostNode;
    }

    public static void main(String[] args) {
        String node = findLowestCostNode();
        while (node != null) {
            Double cost = costs.get(node);
            // Go through all the neighbors of this node
            Map<String, Double> neighbors = graph.get(node);

            for (String n : neighbors.keySet()) {
                double newCost = cost + neighbors.get(n);
                // If it's cheaper to get to this neighbor by going through this node
                if (costs.get(n) > newCost) {
                    // ... update the cost for this node
                    costs.put(n, newCost);
                    // This node becomes the new parent for this neighbor.
                    parents.put(n, node);
                }
            }
            // Mark the node as processed
            processed.add(node);

            // Find the next node to process, and loop
            node = findLowestCostNode();
        }

        System.out.println("Cost from the start to each node:");
        System.out.println(costs); // { a: 5, b: 2, fin: 6 }
    }
}
