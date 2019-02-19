package com.inflean.rest.api.demoinfleanrestapi.event;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by hyuns on 2019-02-19.
 */

public class EventTest {

    /**
     * 빌드 확인
     */
    @Test
    public void builder(){
        Event event = Event.builder()
                .name("infrent")
                .description("rest api")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean(){
        Event event = new Event();
        String spring = "Spring";
        String name = "event";
        event.setName(name);
        event.setDescription(spring);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(spring);
    }
}