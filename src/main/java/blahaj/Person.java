package blahaj;

import java.util.ArrayList;

public class Person {
    public Integer id;
    public ArrayList<String> clubs = new ArrayList<>();
    public Person(String data) {
        data = data.replaceAll("\"", "");
        String[] parts = data.split(",");
        id = Integer.parseInt(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i].trim();
            if (!part.isEmpty() && !part.equals("none")) {
                clubs.add(part);
            }
        }
    }
    @Override
    public String toString() {
        return "id: " + id + ", clubs: " + clubs.toString();
    }
}
