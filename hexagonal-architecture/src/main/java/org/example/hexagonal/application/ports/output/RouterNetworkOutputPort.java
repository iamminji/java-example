package org.example.hexagonal.application.ports.output;

import org.example.hexagonal.domain.entity.Router;
import org.example.hexagonal.domain.vo.RouterId;

public interface RouterNetworkOutputPort {

    Router fetchRouterById(RouterId routerId);

    boolean persistRouter(Router router);

}
