package agh.oop.model;

import javafx.scene.paint.Color;

import java.util.*;

/**
 * Object representing an animal on the map.
 */
public class Animal implements WorldElement {
    private Vector2d position;
    private MapDirection facing;
    private int energy;
    private final List<Animal> children;
    private final Genotype genes;
    private final long birthDate;

    /**
     * Create an Animal with the passed parameters.
     *
     * @param position initial position.
     * @param facing initial direction the animal is facing.
     * @param genes the Genotype of the animal.
     * @param energy initial energy value.
     * @param birthDate the day the animal was created.
     * @see Genotype
     */
    public  Animal(Vector2d position, MapDirection facing, Genotype genes, int energy, long birthDate) {
      this.position = position;
      this.facing = facing;
      this.genes = genes;
      this.energy = energy;
      this.birthDate = birthDate;
      this.children = new ArrayList<>();
    }

    /**
     * @return the day the animal was born.
     */
    public long getBirthDate() {
        return birthDate;
    }

    /**
     * @return the Genotype of the animal.
     * @see Genotype
     */
    public Genotype getGenes() {
        return genes;
    }

    /**
     * @return current energy value of the animal.
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * @return a list of every child of the animal.
     */
    public List<Animal> getChildren() {
        return children;
    }

    /**
     * @return current position of the animal.
     */
    public Vector2d getPosition() {
        return position;
    }

    /**
     * @return the direction the animal is currently facing.
     */
    public MapDirection getFacing() {
        return facing;
    }

    /**
     * @param pos position to check
     * @return true if the animal is at position pos.
     */
    public boolean isAt(Vector2d pos) {
        return position.equals(pos);
    }

    /**
     * @param dir direction to check
     * @return true if the animal is facing dir.
     */
    public boolean isFacing(MapDirection dir) {
        return facing == dir;
    }

    /**
     * Add energy to the animals energy value.
     *
     * @param energy energy to add.
     */
    public void addEnergy(int energy) {
        this.energy += energy;
    }

    /**
     *  Decrements the energy value by 1.
     */
    public void loseEnergy() {
        energy -= 1;
    }

    /**
     *  Decrements the energy value by amount.
     */
    public void loseEnergy(int amount) {
        energy -= amount;
    }

    /**
     * @return true if the animal's energy is <= 0
     */
    public boolean isDead() {
        return energy <= 0;
    }

    /**
     * Move the animal according to its genes.
     *
     * @param validator object correcting the position if
     *                  it's out of bounds.
     */
    public void move(MoveValidator validator) {
        facing = genes.getNextMapDirection(facing);
        Vector2d off = facing.toUnitVector();

        Vector2d newPos = position.add(off);
        position = validator.correctPosition(newPos);
        loseEnergy();
    }

    /**
     * Adds a child to the animal's children list
     *
     * @param child child to add
     */
    public void addChild(Animal child) {
        children.add(child);
    }

    /**
     * Counts every descendant of the animal.
     * May be expensive, as it has to perform a DFS
     * in order to check the children's children etc.
     *
     * @return number of descendants
     */
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

    @Override
    public Shape getShape() {
        return new Shape(Color.rgb(200, 100, 100), ShapeType.CIRCLE, Math.clamp(energy / 10., 0., 1.));
    }
}
