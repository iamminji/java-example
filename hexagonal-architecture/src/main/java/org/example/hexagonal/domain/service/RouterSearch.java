package org.example.hexagonal.domain.service;

import org.example.hexagonal.domain.entity.Router;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RouterSearch {

    public static List<Router> retrieveRouter(List<Router> routers, Predicate<Router> predicate) {
        return routers.stream().filter(predicate).collect(Collectors.toList());
    }
}
