package com.example.trainticketing.repository;

import com.example.trainticketing.model.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RouteRepository {
    private final List<Route> routes = new ArrayList<>();

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }

    public List<Route> getAllRoutes() {
        return routes;
    }

    public Optional<Route> findById(String routeId) {
        return routes.stream()
                .filter(route -> route.getRouteId().equalsIgnoreCase(routeId))
                .findFirst();
    }

    public Optional<Route> findByName(String routeName) {
        return routes.stream()
                .filter(route -> route.getRouteName().equalsIgnoreCase(routeName))
                .findFirst();
    }

    public List<Route> findRoutesBetweenStations(String departureStation, String arrivalStation) {
        return routes.stream()
                .filter(route -> route.hasDirectConnection(departureStation, arrivalStation))
                .toList();
    }
}