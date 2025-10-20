package com.example.lab6.model;

import android.graphics.PointF;
import java.util.List;

public class RoomData {
    private int id;
    private String name;
    private List<PointF> vertices;
    private String description;
    private String image; // nombre de archivo en assets (opcional)

    public RoomData(int id, String name, List<PointF> vertices, String description, String image) {
        this.id = id;
        this.name = name;
        this.vertices = vertices;
        this.description = description;
        this.image = image;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<PointF> getVertices() { return vertices; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
}
