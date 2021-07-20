package com.fogdestination.yourmap;

import java.util.ArrayList;
import java.util.Random;

public class MapData {

    public ArrayList<PlaceInfo> places;
    public Dot[] place_coords;



    public Dot tempDot;

    public MapData() {
        places = new ArrayList<>();
    }

    public void _test_init_places()
    {
        place_coords = new Dot[5];
        for(int i = 0;i<place_coords.length;i++)
        {
            place_coords[i] = Dot.generate_random();
        }

    }

    public void load_data()
    {

    }

    public PlaceInfo addNewPlace(String name,Dot coords,String desc,PlaceType type)
    {
        PlaceInfo newPlace = new PlaceInfo(coords,name,desc,type);
        places.add(newPlace);
        return newPlace;
    }



}

class PlaceInfo{
    private String id;
    protected Dot coords;
    protected String name;
    protected String description;
    protected PlaceType placeType;

    public PlaceInfo() {

    }

    public PlaceInfo(Dot coords, String name, String description, PlaceType placeType) {
        this.coords = coords;
        this.name = name;
        this.description = description;
        this.placeType = placeType;
    }
}

enum PlaceType {Simple}

class Dot{
    private float x;
    private float y;

    private static final float p_min_x = 30f,p_min_y=59.8f,p_max_x=30.5f,p_max_y=60;

    public Dot() {

    }

    public Dot(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Dot(double x,double y)
    {
        this.x = (float) x;
        this.y = (float) y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY() {
        return y;
    }

    public static Dot generate_random()
    {
        Random r = new Random();
        float x = r.nextFloat();
        x = p_min_x + (p_max_x-p_min_x)*x;
        float y = r.nextFloat();
        y = p_min_y + (p_max_y-p_min_y)*y;
        return new Dot(x,y);
    }
}
