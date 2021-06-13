package ru.ruscalworld.points.common.maps;

import ru.ruscalworld.points.common.core.WorldMap;
import ru.ruscalworld.points.common.models.Point;

public class NullWorldMap implements WorldMap {
    @Override
    public boolean addMarker(Point point) {
        return true;
    }

    @Override
    public boolean removeMarker(Point point) {
        return true;
    }

    @Override
    public boolean updateMarker(Point point) {
        return true;
    }
}
