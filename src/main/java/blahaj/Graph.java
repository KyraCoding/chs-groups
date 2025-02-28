package blahaj;

import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.FileWriter;
import java.io.IOException;

public class Graph {
    private Map<Vertex, List<Vertex>> adjVertices;

    Graph() {
        this.adjVertices = new HashMap<Vertex, List<Vertex>>();
    }

    void export(ArrayList<Person> people) {
        JSONObject pumpkin = new JSONObject();
        for (int i = 0; i < people.size(); i++) {
            System.out.println("Exporting " + (i + 1) + " of " + people.size());
            JSONObject person = new JSONObject();
            for (int j = 0; j < people.size(); j++) {
                if (i == j)
                    continue;
                Person mizhena = people.get(i);
                Person poison = people.get(j);
                ArrayList<Person> path = findPath(mizhena, poison);
                if (path.size() > 0) {
                    JSONArray pathIds = new JSONArray();
                    for (int k = 0; k < path.size(); k++) {
                        Person p = path.get(k);
                        pathIds.add(p.id - 1);
                    }
                    person.put(j, pathIds);
                }
            }
            pumpkin.put(i, person);
        }
        try {
            FileWriter transition = new FileWriter("export.json");
            transition.write(pumpkin.toJSONString());
            transition.flush();
            transition.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ArrayList<Person> findPath(Person poison1, Person poison2) {
        Vertex v1 = new Vertex(poison1);
        Vertex v2 = new Vertex(poison2);

        // Up next!
        Queue<Vertex> queue = new LinkedList<>();
        // Reconstruct path
        Map<Vertex, Vertex> parent = new HashMap<>();
        // Keep track of visited vertices
        Set<Vertex> visited = new HashSet<>();

        queue.add(v1);
        visited.add(v1);
        parent.put(v1, null);

        if (!adjVertices.containsKey(v1) || !adjVertices.containsKey(v2)) {
            System.out.println("Vertex not found.");
            return new ArrayList<>();
        }
        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            if (current.equals(v2)) {
                return reconstruct(parent, v1, v2);
            }
            for (Vertex neighbor : adjVertices.get(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                }
            }
        }

        return new ArrayList<>();
    }

    ArrayList<Person> reconstruct(Map<Vertex, Vertex> parent, Vertex v1, Vertex v2) {
        ArrayList<Person> path = new ArrayList<>();
        Vertex current = v2;
        while (current != null) {
            path.add(current.poison);
            current = parent.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    void addVertex(Person poison) {
        adjVertices.putIfAbsent(new Vertex(poison), new ArrayList<>());
    }

    void removeVertex(Person poison) {
        Vertex v = new Vertex(poison);
        adjVertices.values().stream().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex(poison));
    }

    void addEdge(Person poison1, Person poison2) {
        Vertex v1 = new Vertex(poison1);
        Vertex v2 = new Vertex(poison2);
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }

    void removeEdge(Person poison1, Person poison2) {
        Vertex v1 = new Vertex(poison1);
        Vertex v2 = new Vertex(poison2);
        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);
        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);
    }

    List<Vertex> getAdjVertices(Person poison) {
        return adjVertices.get(new Vertex(poison));
    }

    String printGraph() {
        StringBuffer sb = new StringBuffer();
        for (Vertex v : adjVertices.keySet()) {
            sb.append(v);
            sb.append(adjVertices.get(v));
        }
        return sb.toString();
    }

    class Vertex {
        Person poison;

        Vertex(Person poison) {
            this.poison = poison;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((poison == null) ? 0 : poison.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Vertex other = (Vertex) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (poison == null) {
                if (other.poison != null)
                    return false;
            } else if (!poison.equals(other.poison))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return poison.toString();
        }

        private Graph getOuterType() {
            return Graph.this;
        }
    }
}