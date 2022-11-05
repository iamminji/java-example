package org.example.hexagonal.application.usecase;

import org.example.hexagonal.domain.entity.Router;

import java.util.List;
import java.util.function.Predicate;

/**
 * UseCase는 소프트웨어가 하는 일을 설명하는 인터페이스이다.
 */
public interface RouterViewUseCase {
    List<Router> getRouters(Predicate<Router> filter);
}
