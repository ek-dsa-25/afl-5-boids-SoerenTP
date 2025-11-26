package main;

import main.simulation.FlockSimulation;
import main.spatial.*;

import java.util.ArrayList;
import java.util.List;

public class Microbench {
    
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final int WARMUP_STEPS = 50;
    private static final int BENCHMARK_STEPS = 200;
    private static final double NEIGHBOR_RADIUS = 50.0;

    public static void main(String[] args) {
        System.out.println("Starting Boids Microbenchmark...");
        System.out.printf("Dimensions: %dx%d, Radius: %.1f%n", WIDTH, HEIGHT, NEIGHBOR_RADIUS);
        System.out.printf("Warmup: %d steps, Benchmark: %d steps%n%n", WARMUP_STEPS, BENCHMARK_STEPS);
        
        int[] boidCounts = {100, 500, 1000, 2500, 5000};
        
        List<SpatialIndex> spatialIndices = new ArrayList<>();
        spatialIndices.add(new NaiveSpatialIndex());
        spatialIndices.add(new QuadTreeSpatialIndex(WIDTH, HEIGHT));
        spatialIndices.add(new KDTreeSpatialIndex());
        spatialIndices.add(new SpatialHashIndex(WIDTH, HEIGHT, NEIGHBOR_RADIUS));
        
        System.out.println("| Spatial Index   | Boid Count | Avg. Iteration Time (ms) |");
        System.out.println("|-----------------|------------|--------------------------|");

        for (SpatialIndex index : spatialIndices) {
            for (int count : boidCounts) {
                if (index instanceof NaiveSpatialIndex && count > 1000) {
                    System.out.printf("| %-15s | %-10d | %-24s |%n", index.getName(), count, "SKIPPED (Too slow)");
                    continue;
                }

                double avgTime = runBenchmark(index, count);
                System.out.printf("| %-15s | %-10d | %-24.4f |%n", index.getName(), count, avgTime);
            }
        }
    }
    
    private static double runBenchmark(SpatialIndex spatialIndex, int boidCount) {
        FlockSimulation simulation = new FlockSimulation(WIDTH, HEIGHT);
        simulation.setSpatialIndex(spatialIndex);
        simulation.setNeighborRadius(NEIGHBOR_RADIUS);
        simulation.setBoidCount(boidCount);
        
        for (int i = 0; i < WARMUP_STEPS; i++) {
            simulation.update();
        }
        
        double totalTimeMs = 0;
        for (int i = 0; i < BENCHMARK_STEPS; i++) {
            simulation.update();
            totalTimeMs += simulation.getLastIterationTimeMs();
        }

        return totalTimeMs / BENCHMARK_STEPS;
    }
}
