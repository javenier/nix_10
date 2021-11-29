package ua.com.alevel.cities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheapestCostOfPath {

    private static int numOfCities;
    private static List<String> cities;
    private static int INFINITE = Integer.MAX_VALUE;
    private static int currentLine;

    public static List<Integer> findCheapestCostOfPath(String input) {
        List<Integer> result = new ArrayList<>();
        int[][] graph = getMatrix(input);
        String[] tokens = input.split("\\n");
        int waysToFind = Integer.parseInt(tokens[currentLine++]);
        for (int i = 0; i < waysToFind; i++) {
            String[] arr = tokens[currentLine++].split(" ");
            int startPoint, endPoint;
            startPoint = cities.indexOf(arr[0]);
            endPoint = cities.indexOf(arr[1]);
            boolean[] visited = new boolean[numOfCities];
            result.add(solveMinCost(startPoint, endPoint, visited, graph));
        }
        if(result.size() != waysToFind)
            throw new RuntimeException("Actual number of ways is not equal to entered");
        return result;
    }

    private static int solveMinCost(int startPoint, int endPoint, boolean[] visited, int[][] graph) {
        if (startPoint == endPoint)
            return 0;
        visited[startPoint] = true;
        int res = INFINITE;
        for (int i = 0; i < numOfCities; i++) {
            if (graph[startPoint][i] != INFINITE && !visited[i]) {
                int curr = solveMinCost(i,
                        endPoint, visited, graph);
                if (curr < INFINITE) {
                    res = Math.min(res, graph[startPoint][i] + curr);
                }
            }
        }
        visited[startPoint] = false;
        return res;
    }

    private static int[][] getMatrix(String input) {
        currentLine = 1;
        String[] tokens = input.split("\\n");
        numOfCities = Integer.parseInt(tokens[0]);
        int[][] graph = new int[numOfCities][numOfCities];
        cities = new ArrayList<>();
        for (int i = 0; i < numOfCities; i++) {
            cities.add(tokens[currentLine++]);
            int numOfNeighbours = Integer.parseInt(tokens[currentLine++]);
            for (int j = 0; j < numOfNeighbours; j++) {
                String[] arr = tokens[currentLine++].split(" ");
                graph[i][Integer.parseInt(arr[0]) - 1] = Integer.parseInt(arr[1]);
            }
        }
        if(cities.size() != numOfCities)
            throw new RuntimeException("Actual number of cities is not equal to entered");
        fillMatrixWithINF(graph);
        return graph;
    }

    private static void fillMatrixWithINF(int[][] graph) {
        for (int i = 0; i < numOfCities; i++) {
            for (int j = 0; j < numOfCities; j++) {
                if (graph[i][j] == 0)
                    graph[i][j] = INFINITE;
            }
        }
    }
}