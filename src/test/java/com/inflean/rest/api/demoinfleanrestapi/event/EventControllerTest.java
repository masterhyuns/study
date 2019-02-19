package com.inflean.rest.api.demoinfleanrestapi.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hyuns on 2019-02-19.
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTest {

    /**
     * 웹과 관련된 빈들만 등록 해서 사용
     */
    @Autowired
    MockMvc mockMvc;

    @Test
    public void createEvent() throws  Exception{
        mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    )
                //.andExpect(status().is("201"))
                .andExpect(status().isCreated())

                ;
    }




}
