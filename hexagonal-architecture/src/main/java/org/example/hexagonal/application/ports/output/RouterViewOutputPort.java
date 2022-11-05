package org.example.hexagonal.application.ports.output;

import org.example.hexagonal.domain.entity.Router;

import java.util.List;

/**
 * UseCase가 목표를 달성하기 위해 외부 리소스에서 데이터를 가져오야 하는 상황이 있다.
 * 이것이 출력 포트의 역할이다.
 */
public interface RouterViewOutputPort {

    List<Router> fetchRouters();
}
