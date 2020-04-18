import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.HashMap;
import java.util.HashSet;

public class BaseballElimination {
    private final HashMap<String, Integer> names;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] against;
    private int expectedFlow;
    private String leaderTeam;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        if (filename == null) throw new IllegalArgumentException("The argument cannot be null");
        In in = new In(filename);
        int n = in.readInt();
        names = new HashMap<>();
        wins = new int[n];
        losses = new int[n];
        remaining = new int[n];
        against = new int[n][n];
        int maxWin = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            String name = in.readString();
            names.put(name, i);
            wins[i] = in.readInt();
            if (maxWin < wins[i]) {
                maxWin = wins[i];
                leaderTeam = name;
            }
            losses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < n; j++) {
                against[i][j] = in.readInt();
            }
        }
        expectedFlow = 0;
        for (int i = 0; i < numberOfTeams(); i++) {
            for (int j = 0; j < i; j++) {
                expectedFlow += against[i][j];
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return wins.length;
    }

    // all teams
    public Iterable<String> teams() {
        return names.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!names.containsKey(team)) throw new IllegalArgumentException();
        return wins[names.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        if (!names.containsKey(team)) throw new IllegalArgumentException();
        return losses[names.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!names.containsKey(team)) throw new IllegalArgumentException();
        return remaining[names.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!names.containsKey(team1) || !names.containsKey(team2)) throw new IllegalArgumentException();
        return against[names.get(team1)][names.get(team2)];
    }

    private FordFulkerson getSolution(String team) {
        int x = names.get(team);
        int n = numberOfTeams();
        final int s = n, t = n + 1;
        int pivot = t + 1;
        int curMaxWins = wins[x] + remaining[x];
        FlowNetwork network = new FlowNetwork((n * (n + 1)) / 2 + 2);
        for (int i = 0; i < n; i++) {
            int capacity = curMaxWins - wins[i];
            if (capacity < 0) return null;
            network.addEdge(new FlowEdge(i, t, capacity));
            for (int j = 0; j < i; j++) {
                network.addEdge(new FlowEdge(s, pivot, against[i][j]));
                network.addEdge(new FlowEdge(pivot, i, Integer.MAX_VALUE));
                network.addEdge(new FlowEdge(pivot, j, Integer.MAX_VALUE));
                pivot++;
            }
        }
        return(new FordFulkerson(network, s, t));
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return certificateOfElimination(team) != null;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!names.containsKey(team)) throw new IllegalArgumentException();
        FordFulkerson fordFulkerson = getSolution(team);
        HashSet<String> res = new HashSet<>();
        if (fordFulkerson == null) {
            res.add(leaderTeam);
            return res;
        }
        if (fordFulkerson.value() == expectedFlow) return null;
        for (String name : names.keySet()) {
            int i = names.get(name);
            if (fordFulkerson.inCut(i)) res.add(name);
        }
        return res;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("teams12.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
