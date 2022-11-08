package org.example.hexagonal.application.ports.input;

import org.example.hexagonal.application.ports.output.RouterNetworkOutputPort;
import org.example.hexagonal.application.usecase.RouterNetworkUserCase;
import org.example.hexagonal.domain.entity.Router;
import org.example.hexagonal.domain.service.NetworkOperation;
import org.example.hexagonal.domain.vo.Network;
import org.example.hexagonal.domain.vo.RouterId;

public class RouterNetworkInputPort implements RouterNetworkUserCase {

    private final RouterNetworkOutputPort routerNetworkOutputPort;

    public RouterNetworkInputPort(RouterNetworkOutputPort routerNetworkOutputPort) {
        this.routerNetworkOutputPort = routerNetworkOutputPort;
    }

    @Override
    public Router addNetworkToRouter(RouterId routerId, Network network) {
        var router = fetchRouter(routerId);
        return createNetwork(router, network);
    }

    // 다른 메서드는 이 입력 포트의 컨텍스트 외부에서 사용되어서는 안되므로 private 으로 만든다.

    private Router fetchRouter(RouterId routerId) {
        return routerNetworkOutputPort.fetchRouterById(routerId);
    }

    private Router createNetwork(Router router, Network network) {
        // 도메인 서비스와 상호작용
        var newRouter = NetworkOperation.createNewNetwork(router, network);
        // 전체 오퍼레이션의 지속성을 조정
        return persistNetwork(router)? newRouter: router;
    }

    private boolean persistNetwork(Router router) {
        return routerNetworkOutputPort.persistRouter(router);
    }
}
