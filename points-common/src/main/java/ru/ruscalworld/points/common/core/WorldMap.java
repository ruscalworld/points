package ru.ruscalworld.points.common.core;

import ru.ruscalworld.points.common.models.Point;

public interface WorldMap {
    boolean addMarker(Point point);
    boolean removeMarker(Point point);
    boolean updateMarker(Point point);
}
