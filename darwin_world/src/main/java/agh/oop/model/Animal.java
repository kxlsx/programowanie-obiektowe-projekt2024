package agh.oop.model;

import java.util.*;

public class Animal implements WorldElement {
    private Vector2d position;
    private MapDirection facing;
    private int energy;
    private final List<Animal> children;
    private final Genotype genes;
    private final long birthDate;
  
    public Animal(Vector2d position, MapDirection facing, Genotype genes, int energy, long birthDate) {
      this.position = position;
      this.facing = facing;
      this.genes = genes;
      this.energy = energy;
      this.birthDate = birthDate;
      this.children = new ArrayList<>();
    }
  
    public long getBirthDate() {
        return birthDate;
    }
  
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
  
    public boolean isDead() {
        return false; // TODO:
    }

    public void move(MoveValidator validator) {
        facing = genes.getNextMapDirection(facing);
        Vector2d off = facing.toUnitVector();

        Vector2d newPos = position.add(off);
        position = validator.correctPosition(newPos);
        loseEnergy();
    }
  
    public void addChild(Animal child) {
        children.add(child);
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
