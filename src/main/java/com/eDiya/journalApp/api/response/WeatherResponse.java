package com.eDiya.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse{
    public Current current;



    @Getter
    @Setter
    public static class Current{

        @JsonProperty("temp_c")
        private double Temperature;

        private Condition condition;
        @JsonProperty("feelslike_c")
        private double feelsLike;

    }

    @Getter
    @Setter
    public static class Condition{
        private String text;
        private String icon;
        private int code;
    }

}




