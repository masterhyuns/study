package com.inflean.rest.api.demoinfleanrestapi.event;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hyuns on 2019-02-19.
 */
public interface EventRepository extends JpaRepository<Event,Integer> {
}
