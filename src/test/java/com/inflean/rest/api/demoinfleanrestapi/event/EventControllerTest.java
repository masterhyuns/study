package com.inflean.rest.api.demoinfleanrestapi.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inflean.rest.api.demoinfleanrestapi.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by hyuns on 2019-02-19.
 */
@RunWith(SpringRunner.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    /**
     * 웹과 관련된 빈들만 등록 해서 사용
     */
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
/*
    @MockBean
    EventRepository eventRepository;*/


    @Test
    @TestDescription("정상적으로 이벤트를 생성 하는 테스트")
    public void createEvent() throws  Exception{
        Event event = Event.builder()
                .name("Spring")
                .description("Rest APi")
                .beginEnrollmentDateTime(LocalDateTime.of(2018,11,20,11,00))
                .closeEnrollmentDateTime(LocalDateTime.of(2018,11,21,11,00))
                .beginEventDateTime(LocalDateTime.of(2018,11,21,11,00))
                .endEventDateTime(LocalDateTime.of(2018,11,21,11,00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역")
                .build();

        // Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event))
                    )
                //.andExpect(status().is("201"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists()) // id 가 있는지
                .andExpect(header().exists("Location"))
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id").value((Matchers.not(100))))
                .andExpect(jsonPath("offline").value((Matchers.not(true))))
        .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                ;
    }



    @Test
    @TestDescription("입력 받을 수 없는 값을 사용한 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request() throws  Exception{
        Event event = Event.builder()
                .name("Spring")
                .id(100)
                .description("Rest APi")
                .beginEnrollmentDateTime(LocalDateTime.of(2018,11,20,11,00))
                .closeEnrollmentDateTime(LocalDateTime.of(2018,11,21,11,00))
                .beginEventDateTime(LocalDateTime.of(2018,11,21,11,00))
                .endEventDateTime(LocalDateTime.of(2018,11,21,11,00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역")
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        // Mockito.when(eventRepository.save(event)).thenReturn(event);

                mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())

        ;
    }

    @Test
    @TestDescription("입력값이 비어 있는 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(eventDto))
                )
                .andExpect(status().isBadRequest())
                ;
    }

    /**
     * 입력값이 잘못된 경우에 에러가 발생하는 테스트
     * @throws Exception
     */
    @Test
    @TestDescription("입력값이 잘못된 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Empty_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("Rest APi")
                .beginEnrollmentDateTime(LocalDateTime.of(2018,11,26,11,00))
                .closeEnrollmentDateTime(LocalDateTime.of(2018,11,25,11,00))
                .beginEventDateTime(LocalDateTime.of(2018,11,24,11,00))
                .endEventDateTime(LocalDateTime.of(2018,11,23,11,00))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역")
                .build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(eventDto))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
        ;
    }



}
