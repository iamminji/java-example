package org.example.hexagonal.application.ports.input;

import org.example.hexagonal.application.usecase.RouterViewUseCase;
import org.example.hexagonal.domain.entity.Router;
import org.example.hexagonal.application.ports.output.RouterViewOutputPort;
import org.example.hexagonal.domain.service.RouterSearch;

import java.util.List;
import java.util.function.Predicate;

/**
 * UseCase 인터페이스를 구현하는 역할이다.
 */
public class RouterViewInputPort implements RouterViewUseCase {

    private RouterViewOutputPort routerListOutputPort;

    public RouterViewInputPort(RouterViewOutputPort routerListOutputPort) {
        this.routerListOutputPort = routerListOutputPort;
    }

    @Override
    public List<Router> getRouters(Predicate<Router> filter) {
        var routers = routerListOutputPort.fetchRouters();
        return RouterSearch.retrieveRouter(routers, filter);
    }
}
