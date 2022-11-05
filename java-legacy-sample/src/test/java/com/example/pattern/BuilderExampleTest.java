package com.example.pattern;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BuilderExampleTest {

    @Test
    public void 빌더_테스트() {
        BuilderExample example = new BuilderExample.Builder(3)
                .name("테스트 얍얍")
                .build();

        assertThat(example.getId()).isEqualTo(3);
        assertThat(example.getName()).isEqualTo("테스트 얍얍");
        assertThat(example.getValue()).isNull();
    }
}