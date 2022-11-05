package org.example.hexagonal.framework.adapter;

import org.example.hexagonal.domain.vo.RouterType;
import org.example.hexagonal.application.ports.input.RouterViewInputPort;
import org.example.hexagonal.application.usecase.RouterViewUseCase;
import org.example.hexagonal.domain.entity.Router;

import java.util.List;

public class RouterViewCLIAdapter {
    RouterViewUseCase routerViewUseCase;

    public RouterViewCLIAdapter() {
        setAdapters();
    }

    public List<Router> obtainRelatedRouters(String type) {
        return routerViewUseCase.getRouters(
                Router.filterRouterByType(RouterType.valueOf(type))
        );
    }

    private void setAdapters() {
        this.routerViewUseCase =
                new RouterViewInputPort(RouterViewFileAdapter.getInstance());

    }

}
