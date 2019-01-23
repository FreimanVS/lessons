package com.vfreiman.lessons._1_algorithms_and_data_structures;

import java.util.*;

public class BreadthFirstSearch {

    private static final Map<String, List<String>> graph = new HashMap<String, List<String>>() {
        {
            put("you", Arrays.asList("Alice", "Claire", "Bob"));
            put("Alice", Arrays.asList("Peggy"));
            put("Claire", Arrays.asList("Johny", "Tom"));
            put("Bob", Arrays.asList("Peggy", "Anudj", "Bob"));
            put("Peggy", Collections.emptyList());
            put("Anudj", Collections.emptyList());
            put("Johny", Collections.emptyList());
            put("Tom", Collections.emptyList());
        }
    };

    private static boolean bfs(String name) {
        List<String> friends = graph.get(name);
//        friends.forEach(search_que::offerFirst);
        Deque<String> search_que = new ArrayDeque<>(friends);

        Set<String> searched = new HashSet<>();

        while (!search_que.isEmpty()) {
            String person = search_que.pollLast();
            if (!searched.contains(person)) {
                if (personIsSeller(person)) {
                    System.out.println(person + " is a mango seller!");
                    return true;
                } else {
                    friends = graph.get(person);
//                    friends.forEach(search_que::offerFirst);
                    search_que.addAll(friends);

                    searched.add(person);
                }
            }
        }
        return false;
    }

    private static boolean personIsSeller(String person) {
        return person.startsWith("P");
    }

    public static void main(String[] args) {
        bfs("you");
    }
}
