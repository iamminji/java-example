package org.example.hexagonal.domain.service;

import org.example.hexagonal.domain.entity.Router;
import org.example.hexagonal.domain.specification.CIDRSpecification;
import org.example.hexagonal.domain.specification.NetworkAmountSpecification;
import org.example.hexagonal.domain.specification.NetworkAvailabilitySpecification;
import org.example.hexagonal.domain.specification.RouterTypeSpecification;
import org.example.hexagonal.domain.vo.IP;
import org.example.hexagonal.domain.vo.Network;

public class NetworkOperation {

    public void createNetwork(Router router, IP address, String name, int cidr) {
        var availabilitySpec = new NetworkAvailabilitySpecification(address, name, cidr);
        var cidrSpec = new CIDRSpecification();
        var routerTypeSpec = new RouterTypeSpecification();
        var amountSpec = new NetworkAmountSpecification();

        if (!cidrSpec.isSatisfiedBy(cidr)) {
            throw new IllegalArgumentException("CIDR is below " + CIDRSpecification.MINIMUM_ALLOWED_CIDR);
        }

        if (!availabilitySpec.isSatisfiedBy(router)) {
            throw new IllegalArgumentException("Address already exist");
        }

        if (amountSpec.and(routerTypeSpec).isSatisfiedBy(router)) {
            Network network = router.createNetwork(address, name, cidr);
            router.addNetworkToSwitch(network);
        }

    }
}
