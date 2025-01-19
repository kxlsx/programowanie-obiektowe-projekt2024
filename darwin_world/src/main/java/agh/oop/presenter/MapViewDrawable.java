package agh.oop.presenter;

import agh.oop.model.Shape;
import agh.oop.model.Vector2d;
import agh.oop.model.WorldElement;

public record MapViewDrawable(Shape shape, Vector2d position, WorldElement worldElement) {

}
