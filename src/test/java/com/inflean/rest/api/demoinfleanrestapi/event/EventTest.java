package com.inflean.rest.api.demoinfleanrestapi.event;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by hyuns on 2019-02-19.
 */
@RunWith(JUnitParamsRunner.class)
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


    private Object[] parametersForTestFree(){
        return new Object[]{
            new Object[] {0,0,true},
            new Object[] {100,0,false},
            new Object[] {0,100,false},
            new Object[] {100,200,false},
        };
    }

    @Test
    /*@Parameters({
            "0,0,true",
            "100,0,false",
            "0,100,false"
    })*/

    @Parameters
    public void testFree(int basePrice,int maxPrice, boolean isFree){
        // Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    @Test
    @Parameters
    public void TestOffLine(String location,boolean isOffline){
        // Given
        Event event = Event.builder()
                .location(location)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isEqualTo(isOffline);

    }

    private Object[] parametersForTestOffLine(){
        return new Object[]{
                new Object[] {"강남",true},
                new Object[] {null,false},
                new Object[] {"         ",false},
        };
    }
}