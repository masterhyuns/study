package com.inflean.rest.api.demoinfleanrestapi.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inflean.rest.api.demoinfleanrestapi.common.RestDocConfiguration;
import com.inflean.rest.api.demoinfleanrestapi.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
@AutoConfigureRestDocs
@Import(RestDocConfiguration.class)
@ActiveProfiles("test")
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
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("Rest APi")
                .beginEnrollmentDateTime(LocalDateTime.of(2018,11,20,11,00))
                .closeEnrollmentDateTime(LocalDateTime.of(2018,11,21,11,00))
                .beginEventDateTime(LocalDateTime.of(2018,11,22,11,00))
                .endEventDateTime(LocalDateTime.of(2018,11,24,11,00))
                .location("강남역")
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .build();

        // Mockito.when(eventRepository.save(event)).thenReturn(event);

       this.mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event))
                    )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists()) // id 가 있는지
                .andExpect(header().exists("Location"))
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                .andDo(document("create-event",
                    links(linkWithRel("self").description("link to self"),
                        linkWithRel("query-events").description("link to query events"),
                        linkWithRel("update-event").description("link to update events"),
                        linkWithRel("profile").description("link to profile")

                    ),
                    // 요청 헤더
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content-type header")
                    ),
                    // 요청 필드
                    requestFields(
                        fieldWithPath("name").description("Name of new event"),
                        fieldWithPath("description").description("Name of new event"),
                        fieldWithPath("beginEnrollmentDateTime").description("date time begin of new event"),
                        fieldWithPath("closeEnrollmentDateTime").description("date time begin of close event"),
                        fieldWithPath("beginEventDateTime").description("beginEventDateTime"),
                        fieldWithPath("endEventDateTime").description("endEventDateTime"),
                        fieldWithPath("limitOfEnrollment").description("limitOfEnrollment"),
                        fieldWithPath("location").description("location"),
                        fieldWithPath("basePrice").description("basePrice"),
                        fieldWithPath("maxPrice").description("maxPrice"),
                        fieldWithPath("maxPrice").description("maxPrice")
                    ),
                    responseHeaders(
                            headerWithName(HttpHeaders.LOCATION).description("location header"),
                            headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")

                    ),
                    // 응답의 일부분만 할때는 relaxedResponseFields 사용
                    responseFields(
                            fieldWithPath("id").description("id"),
                            fieldWithPath("name").description("Name of new event"),
                            fieldWithPath("description").description("Name of new event"),
                            fieldWithPath("beginEnrollmentDateTime").description("date time begin of new event"),
                            fieldWithPath("closeEnrollmentDateTime").description("date time begin of close event"),
                            fieldWithPath("beginEventDateTime").description("beginEventDateTime"),
                            fieldWithPath("endEventDateTime").description("endEventDateTime"),
                            fieldWithPath("limitOfEnrollment").description("limitOfEnrollment"),
                            fieldWithPath("location").description("location"),
                            fieldWithPath("basePrice").description("basePrice"),
                            fieldWithPath("maxPrice").description("maxPrice"),
                            fieldWithPath("maxPrice").description("maxPrice"),
                            fieldWithPath("free").description("free"),
                            fieldWithPath("offline").description("offline"),
                            fieldWithPath("eventStatus").description("eventStatus"),

                            fieldWithPath("_links.self.href").description("eventStatus"),
                            fieldWithPath("_links.query-events.href").description("eventStatus"),
                            fieldWithPath("_links.update-event.href").description("eventStatus"),
                            fieldWithPath("_links.profile.href").description("profile")
                    )
                ))
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
