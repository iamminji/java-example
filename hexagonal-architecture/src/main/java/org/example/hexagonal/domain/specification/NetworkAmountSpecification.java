package org.example.hexagonal.domain.specification;

import org.example.hexagonal.domain.entity.Router;

public class NetworkAmountSpecification extends AbstractSpecification<Router> {

    public final static int MAXIMUM_ALLOWED_NETWORKS = 6;

    @Override
    public boolean isSatisfiedBy(Router router) {
        return router.retrieveNetworks().size() <= MAXIMUM_ALLOWED_NETWORKS;
    }
}
