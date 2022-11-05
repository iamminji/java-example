package org.example.hexagonal.domain.specification;

public class CIDRSpecification extends AbstractSpecification<Integer> {

    public final static int MINIMUM_ALLOWED_CIDR = 8;

    @Override
    public boolean isSatisfiedBy(Integer cidr) {
        return cidr > MINIMUM_ALLOWED_CIDR;
    }
}
