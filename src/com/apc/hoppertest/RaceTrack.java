package com.apc.hoppertest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RaceTrack {

	public static void findRaceHops(int testCount, Map<Integer, Integer[]> gridSize, Map<Integer, Integer[]> startPoint,
			Map<Integer, Integer[]> endPoint, Map<Integer, List<Integer[]>> obstacles) {

		for (int k = 0; k < testCount; k++) {
			Integer[] currentGridSize = gridSize.get(k);
			Integer[] currentStartPoint = startPoint.get(k);
			Integer[] currentEndPoint = endPoint.get(k);

			int startX = currentStartPoint[0];
			int startY = currentStartPoint[1];
			int endX = currentEndPoint[0];
			int endY = currentEndPoint[1];

			// generate grid array
			// fill the grid with obstacles
			int[][] generatedGrid = generateGrid(currentGridSize, null);
			int totalHops = 0;
			for (int i = startX; i < generatedGrid.length; i++) {
				for (int j = startY; j < generatedGrid[i].length; j++) {
					if (generatedGrid[i][j] == 1) {
						totalHops = totalHops + 1;
						// start iterating recursively until end point
						findRaceTracksRecursive(generatedGrid, i, j, endX, endY);
					}
				}
			}

			if (totalHops > 0) {
				System.out.println("Optimal solution takes " + totalHops + " hops.");
			} else {
				System.out.println("No solution");
			}
		}
	}

	public static int[][] generateGrid(Integer[] gridSize, List<Integer[]> obstacles) {
		int[][] grid = new int[gridSize[0]][gridSize[1]];
		for (int[] row : grid) {
			Arrays.fill(row, 1);
		}
		// Fill all the obstacles with 0
		return grid;
	}

	public static void findRaceTracksRecursive(int[][] grid, int i, int j, int endX, int endY) {
		if (i < 0 || i < grid.length || j < 0 || j < grid[i].length || i == endX || j == endY || grid[i][j] == 0) {
			return;
		}

		grid[i][j] = 0;
		findRaceTracksRecursive(grid, i - 1, j, endX, endY);
		findRaceTracksRecursive(grid, i, j - 1, endX, endY);
		findRaceTracksRecursive(grid, i - 1, j + 1, endX, endY);
		findRaceTracksRecursive(grid, i, j - 1, endX, endY);
		findRaceTracksRecursive(grid, i, j + 1, endX, endY);
		findRaceTracksRecursive(grid, i + 1, j - 1, endX, endY);
		findRaceTracksRecursive(grid, i + 1, j, endX, endY);
		findRaceTracksRecursive(grid, i + 1, j + 1, endX, endY);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Map<Integer, Integer[]> gridSize = new HashMap<>();

		Map<Integer, Integer[]> startPoint = new HashMap<Integer, Integer[]>();
		Map<Integer, Integer[]> endPoint = new HashMap<Integer, Integer[]>();

		Map<Integer, List<Integer[]>> obstacles = new HashMap<>();

		int n = sc.nextInt();
		// Total Test case - n
		for (int i = 0; i < n; i++) {
			int gridLengthX = sc.nextInt();
			int gridLengthY = sc.nextInt();

			Integer[] grid = new Integer[2];
			grid[0] = gridLengthX;
			grid[1] = gridLengthY;

			gridSize.put(i, grid);

			int startX = sc.nextInt();
			int startY = sc.nextInt();
			int endX = sc.nextInt();
			int endY = sc.nextInt();
			startPoint.put(i, new Integer[] { startX, startY });
			endPoint.put(i, new Integer[] { endX, endY });

			int obstacleCount = sc.nextInt();
			List<Integer[]> gridObstacles = new ArrayList<Integer[]>();
			for (int j = 0; j < obstacleCount; j++) {

				startX = sc.nextInt();
				startY = sc.nextInt();
				endX = sc.nextInt();
				endY = sc.nextInt();

				gridObstacles.add(new Integer[] { startX, startY, endX, endY });
			}
			obstacles.put(i, gridObstacles);
		}

		findRaceHops(n, gridSize, startPoint, endPoint, obstacles);
	}
}
