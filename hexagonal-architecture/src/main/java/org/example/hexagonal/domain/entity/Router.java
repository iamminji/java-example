package org.example.hexagonal.domain.entity;

import org.example.hexagonal.domain.vo.RouterId;
import org.example.hexagonal.domain.vo.RouterType;
import org.example.hexagonal.domain.vo.IP;
import org.example.hexagonal.domain.vo.Network;

import java.util.List;
import java.util.function.Predicate;

/**
 * Aggregate Root
 * 애그리게잇 루트 컨텍스트 하위의 모든 객체(Switch, Network...) 를 처리하는 책임은 이제 Router가 가지게 된다.
 */
public class Router {
    private final RouterType routerType;
    private final RouterId routerId;
    private Switch networkSwitch;

    public Router(RouterType routerType, RouterId routerId) {
        this.routerType = routerType;
        this.routerId = routerId;
    }

    public static Predicate<Router> filterRouterByType(RouterType routerType) {
        return routerType.equals(RouterType.CORE)
                ? isCore() :
                isEdge();
    }

    private static Predicate<Router> isCore() {
        return p -> p.getRouterType() == RouterType.CORE;
    }

    private static Predicate<Router> isEdge() {
        return p -> p.getRouterType() == RouterType.EDGE;
    }

    public void addNetworkToSwitch(Network network) {
        this.networkSwitch = networkSwitch.addNetwork(network);
    }

    public Network createNetwork(IP address, String name, int cidr) {
        return new Network(address, name, cidr);
    }

    public List<Network> retrieveNetworks() {
        return networkSwitch.getNetworks();
    }

    public RouterType getRouterType() {
        return routerType;
    }
}
