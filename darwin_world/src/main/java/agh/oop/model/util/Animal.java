package agh.oop.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Animal implements WorldElement {
    private Vector2d position;
    private MapDirection facing;
    private Genotype genes;
    private int energy;
    private List<Animal> children;

    public Genotype getGenes() {
        return genes;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Animal> getChildren() {
        return children;
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getFacing() {
        return facing;
    }

    public boolean isAt(Vector2d pos) {
        return position.equals(pos);
    }

    public boolean isFacing(MapDirection dir) {
        return facing == dir;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }

    private void loseEnergy() {
        energy -= 1; //TODO: check if its by 1
    }

    public void move(MoveValidator validator) {
        facing = genes.getNextMapDirection(facing);
        Vector2d off = facing.toUnitVector();

        Vector2d newPos = position.add(off);
        position = validator.correctPosition(newPos);
        loseEnergy();
    }

    public int countDescendants() {
        HashSet<Animal> visited = new HashSet<>();
        Stack<Animal> stack = new Stack<>();
        int descCount = 0;

        visited.add(this);
        stack.push(this);
        while(!stack.empty()) {
            Animal v = stack.pop();

            for(Animal child : v.getChildren()) {
                if(!visited.contains(child)) {
                    visited.add(child);
                    stack.push(child);
                    descCount += 1;
                }
            }
        }
        return descCount;
    }
}
