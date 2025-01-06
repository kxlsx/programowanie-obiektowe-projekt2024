package agh.oop.model;

import java.util.List;


// wydaje mi sie, ze mapa powina byc mapa setow animali

public class WorldMap implements MoveValidator{

    // po to, zeby nie iterowac po calej mapie zwraca po prostu zbior pozycji animali
    public List<Vector2d> getAnimalsPositions() {}

    public List<Animal> animalsAt(Vector2d position) {}

    public void removeAnimal(Animal animal) {}

    public void addAnimal(Animal animal) {}

    // mysle, ze move validator jest niepotrzebny i mapa sama sobie bedzie radzic
    public void moveAnimal(Animal animal) {}

    // tutaj zalozylem, ze jest tylko jeden plant ale tego nie jestem pewien
    public Plant plantAt(Vector2d position) {}

    public void addPlant(Plant plant) {}

    public void removePlant(Plant plant) {}


    @Override
    public Vector2d correctPosition(Vector2d position) {
        //TODO:
        return null;
    }
}
