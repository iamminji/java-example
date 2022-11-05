package org.example.hexagonal.domain.vo;

import java.util.UUID;

public class RouterId {

    private UUID id;

    public RouterId(UUID id) {
        this.id = id;
    }

    /**
     * ID가 있는 경우 Router Entity의 재구성을 허용한다.
     */
    public static RouterId withId(String id) {
        return new RouterId(UUID.fromString(id));
    }

    /**
     * ID 없이 새로운 ID를 생성하여 Router Entity를 만든다.
     */
    public static RouterId withoutId() {
        return new RouterId(UUID.randomUUID());
    }
}
