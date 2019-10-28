package com.example.i411_10.smart_fish;

/**
 * Created by I411-10 on 2016-07-11.
 */
public class Fish {
    private String fish_temp;
    private String fish_name;


    public Fish(String fish_temp, String fish_name) {
        this.fish_temp = fish_temp;
        this.fish_name = fish_name;
    }

    public String getName() { return this.fish_name; }

    public String getTemp() { return this.fish_temp; }

}