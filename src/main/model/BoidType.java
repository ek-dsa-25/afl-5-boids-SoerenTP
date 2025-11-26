package main.model;

import main.behavior.BehaviorStrategy;
import main.behavior.FlockBehavior;
import main.behavior.FlockWeights;
import main.behavior.RandomBehavior;

import java.awt.Color;

public enum BoidType {
    STANDARD(Color.WHITE),
    RANDOM_WALKER(Color.GREEN);

    private final Color color;

    BoidType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    
    public static BehaviorStrategy getStrategyForType(BoidType type) {
        if (type == RANDOM_WALKER) {
            return new RandomBehavior();
        }
        
        return new FlockBehavior(FlockWeights.standard());
    }
}
