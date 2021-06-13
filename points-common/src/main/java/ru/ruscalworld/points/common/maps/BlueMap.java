package ru.ruscalworld.points.common.maps;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.marker.MarkerAPI;
import de.bluecolored.bluemap.api.marker.MarkerSet;
import de.bluecolored.bluemap.api.marker.POIMarker;
import ru.ruscalworld.points.common.core.WorldMap;
import ru.ruscalworld.points.common.models.Point;
import ru.ruscalworld.points.common.util.Location;

import java.io.IOException;
import java.util.Optional;

public class BlueMap implements WorldMap {
    @Override
    public boolean addMarker(Point point) {
        return this.updateMarker(point);
    }

    @Override
    public boolean removeMarker(Point point) {
        boolean deleted;

        try {
            MarkerAPI markerAPI = this.getBlueMapAPI().getMarkerAPI();
            MarkerSet points = this.getMarkerSet(markerAPI);
            deleted = points.removeMarker(point.getSlug());
            markerAPI.save();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return deleted;
    }

    @Override
    public boolean updateMarker(Point point) {
        try {
            MarkerAPI markerAPI = this.getBlueMapAPI().getMarkerAPI();
            MarkerSet points = this.getMarkerSet(markerAPI);
            System.out.println(points.getLabel());

            Location location = point.getLocation();
            Optional<BlueMapMap> map = this.getBlueMapAPI().getMap(location.getWorldName());
            if (!map.isPresent()) return false;

            POIMarker poiMarker = points.createPOIMarker(point.getSlug(), map.get(), location.getLocation());
            poiMarker.setLabel(point.getName());
            System.out.println(poiMarker.getId());
            markerAPI.save();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private MarkerSet getMarkerSet(MarkerAPI markerAPI) throws IOException {
        Optional<MarkerSet> set = markerAPI.getMarkerSet("points");
        return set.orElseGet(() -> {
            MarkerSet points = markerAPI.createMarkerSet("points");
            points.setLabel("Points");
            return points;
        });
    }

    private BlueMapAPI getBlueMapAPI() {
        Optional<BlueMapAPI> api = BlueMapAPI.getInstance();
        if (!api.isPresent()) throw new IllegalStateException("BlueMap API is not loaded");
        return api.get();
    }
}
