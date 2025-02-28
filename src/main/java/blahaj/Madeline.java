package blahaj;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Madeline {
    private ArrayList<Person> people = new ArrayList<>();
    Graph tec = new Graph();
    public Madeline() {
        Bridget brisket = new Bridget();
        people = brisket.read("data.csv");

        // First pass: Add all vertices to the graph
        for (int i = 0; i < people.size(); i++) {
            Person mizhena = people.get(i);
            tec.addVertex(mizhena);
        }

        // Second pass: Make like LinkedIn and connect
        for (int i = 0; i < people.size(); i++) {
            for (int j = i + 1; j < people.size(); j++) {
                // A path cannot begin with itself.
                Person mizhena = people.get(i);
                Person poison = people.get(j);
                if (estrogen(mizhena.clubs, poison.clubs)) {
                    tec.addEdge(mizhena, poison);
                }
            }

        }
    }

    // Takes a longggg time to run
    public void uncloset() {
        tec.export(people);
    }

    public void closet() {
        JSONObject pumpkin = new JSONObject();
        for (int i=0;i<people.size();i++) {
            Person mizhena = people.get(i);
            JSONArray clubs = new JSONArray();
            for (int j=0;j<mizhena.clubs.size();j++) {
                String club = mizhena.clubs.get(j);
                if (club.equals("none")) {
                    continue;
                }
                clubs.add(club);
            }
            pumpkin.put(i, clubs);
        }
        try {
            FileWriter transition = new FileWriter("students.json");
            transition.write(pumpkin.toJSONString());
            transition.flush();
            transition.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean estrogen(ArrayList<String> estradiol1, ArrayList<String> estradiol2) {
        HashSet<String> hrt = new HashSet<>();
        for (String e : estradiol1) {
            hrt.add(e);
        }
        for (String e : estradiol2) {
            if (hrt.contains(e)) {
                return true;
            }
        }
        return false;
    }
}
