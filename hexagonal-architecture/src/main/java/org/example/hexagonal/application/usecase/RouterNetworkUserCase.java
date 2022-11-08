package org.example.hexagonal.application.usecase;

import org.example.hexagonal.domain.entity.Router;
import org.example.hexagonal.domain.vo.Network;
import org.example.hexagonal.domain.vo.RouterId;

public interface RouterNetworkUserCase {

    Router addNetworkToRouter(RouterId routerId, Network network);

}
