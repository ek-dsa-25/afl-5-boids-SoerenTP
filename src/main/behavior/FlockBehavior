package main.behavior;

import main.model.Boid;
import main.simulation.Forces;
import main.simulation.Vector2D;

import java.util.List;

public class FlockBehavior implements BehaviorStrategy {

    private final FlockWeights weights;

    public FlockBehavior(FlockWeights weights) {
        this.weights = weights;
    }

    public FlockWeights getFlockWeights() {
        return weights;
    }

    @Override
    public Forces calculateForces(Boid boid, List<Boid> neighbors) {
        if (neighbors.isEmpty()) {
            return new Forces();
        }

        Vector2D separation = calculateSeparation(boid, neighbors, weights);
        Vector2D alignment = calculateAlignment(boid, neighbors, weights);
        Vector2D cohesion = calculateCohesion(boid, neighbors, weights);

        return new Forces(separation, alignment, cohesion);
    }

    // All logic below is moved from Boid.java
    // It now takes a 'Boid boid' parameter to get its state
    // It accesses Boid.MAX_SPEED and Boid.MAX_FORCE directly

    private Vector2D calculateSeparation(Boid boid, List<Boid> neighbors, FlockWeights weights) {
        double steerX = 0, steerY = 0;
        int count = 0;

        for (Boid neighbor : neighbors) {
            double distance = boid.distanceTo(neighbor);
            if (distance > 0 && distance < 25) { // Separation check distance
                double diffX = boid.getX() - neighbor.getX();
                double diffY = boid.getY() - neighbor.getY();

                diffX /= distance;
                diffY /= distance;

                steerX += diffX;
                steerY += diffY;
                count++;
            }
        }

        if (count > 0) {
            steerX /= count;
            steerY /= count;

            double magnitude = Math.sqrt(steerX * steerX + steerY * steerY);
            if (magnitude > 0) {
                steerX = (steerX / magnitude) * Boid.MAX_SPEED;
                steerY = (steerY / magnitude) * Boid.MAX_SPEED;

                steerX -= boid.getVx();
                steerY -= boid.getVy();

                double force = Math.sqrt(steerX * steerX + steerY * steerY);
                if (force > Boid.MAX_FORCE) {
                    steerX = (steerX / force) * Boid.MAX_FORCE;
                    steerY = (steerY / force) * Boid.MAX_FORCE;
                }
            }
        }

        return new Vector2D(steerX * weights.separation(), steerY * weights.separation());
    }

    private Vector2D calculateAlignment(Boid boid, List<Boid> neighbors, FlockWeights weights) {
        double avgVx = 0, avgVy = 0;
        int count = 0;

        for (Boid neighbor : neighbors) {
            double distance = boid.distanceTo(neighbor);
            if (distance > 0 && distance < 50) { // Alignment check distance
                avgVx += neighbor.getVx();
                avgVy += neighbor.getVy();
                count++;
            }
        }

        if (count > 0) {
            avgVx /= count;
            avgVy /= count;

            double magnitude = Math.sqrt(avgVx * avgVx + avgVy * avgVy);
            if (magnitude > 0) {
                avgVx = (avgVx / magnitude) * Boid.MAX_SPEED;
                avgVy = (avgVy / magnitude) * Boid.MAX_SPEED;

                double steerX = avgVx - boid.getVx();
                double steerY = avgVy - boid.getVy();

                double force = Math.sqrt(steerX * steerX + steerY * steerY);
                if (force > Boid.MAX_FORCE) {
                    steerX = (steerX / force) * Boid.MAX_FORCE;
                    steerY = (steerY / force) * Boid.MAX_FORCE;
                }

                return new Vector2D(steerX * weights.alignment(), steerY * weights.alignment());
            }
        }

        return Vector2D.ZERO;
    }

    private Vector2D calculateCohesion(Boid boid, List<Boid> neighbors, FlockWeights weights) {
        double centerX = 0, centerY = 0;
        int count = 0;

        for (Boid neighbor : neighbors) {
            double distance = boid.distanceTo(neighbor);
            if (distance > 0 && distance < 50) { // Cohesion check distance
                centerX += neighbor.getX();
                centerY += neighbor.getY();
                count++;
            }
        }

        if (count > 0) {
            centerX /= count;
            centerY /= count;

            double steerX = centerX - boid.getX();
            double steerY = centerY - boid.getY();

            double magnitude = Math.sqrt(steerX * steerX + steerY * steerY);
            if (magnitude > 0) {
                steerX = (steerX / magnitude) * Boid.MAX_SPEED;
                steerY = (steerY / magnitude) * Boid.MAX_SPEED;

                steerX -= boid.getVx();
                steerY -= boid.getVy();

                double force = Math.sqrt(steerX * steerX + steerY * steerY);
                if (force > Boid.MAX_FORCE) {
                    steerX = (steerX / force) * Boid.MAX_FORCE;
                    steerY = (steerY / force) * Boid.MAX_FORCE;
                }

                return new Vector2D(steerX * weights.cohesion(), steerY * weights.cohesion());
            }
        }

        return Vector2D.ZERO;
    }
}
