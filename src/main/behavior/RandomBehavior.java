package main.behavior;

import main.model.Boid;
import main.simulation.Forces;
import main.simulation.Vector2D;

import java.util.List;

public class RandomBehavior implements BehaviorStrategy {
    
    private static final double MAX_ANGLE_CHANGE = 0.5;

    @Override
    public Forces calculateForces(Boid boid, List<Boid> neighbors) {
        
        double currentAngle = Math.atan2(boid.getVy(), boid.getVx());
        
        double angleChange = (Math.random() - 0.5) * MAX_ANGLE_CHANGE;
        double newAngle = currentAngle + angleChange;
        
        double targetVx = Math.cos(newAngle) * Boid.MAX_SPEED;
        double targetVy = Math.sin(newAngle) * Boid.MAX_SPEED;
        
        double steerX = targetVx - boid.getVx();
        double steerY = targetVy - boid.getVy();
        
        double forceMag = Math.sqrt(steerX * steerX + steerY * steerY);
        if (forceMag > Boid.MAX_FORCE) {
            steerX = (steerX / forceMag) * Boid.MAX_FORCE;
            steerY = (steerY / forceMag) * Boid.MAX_FORCE;
        }
        
        return new Forces(Vector2D.ZERO, new Vector2D(steerX, steerY), Vector2D.ZERO);
    }
}
