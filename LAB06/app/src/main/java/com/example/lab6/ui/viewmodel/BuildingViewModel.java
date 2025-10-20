package com.example.lab6.ui.viewmodel;

import android.app.Application;
import android.graphics.PointF;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.lab6.model.RoomData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BuildingViewModel extends AndroidViewModel {
    public MutableLiveData<List<RoomData>> rooms = new MutableLiveData<>();
    public MutableLiveData<RoomData> selectedRoom = new MutableLiveData<>();

    public BuildingViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadRoomsFromAssets() {
        try {
            InputStream is = getApplication().getAssets().open("rooms.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            br.close();

            JSONArray array = new JSONArray(sb.toString());
            List<RoomData> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                String description = obj.optString("description", "");
                String image = obj.optString("image", null);

                JSONArray verts = obj.getJSONArray("vertices");
                List<PointF> points = new ArrayList<>();
                for (int j = 0; j < verts.length(); j++) {
                    JSONObject v = verts.getJSONObject(j);
                    float x = (float) v.getDouble("x");
                    float y = (float) v.getDouble("y");
                    points.add(new PointF(x, y));
                }
                list.add(new RoomData(id, name, points, description, image));
            }
            rooms.postValue(list);
        } catch (Exception e) {
            e.printStackTrace();
            rooms.postValue(new ArrayList<RoomData>());
        }
    }

    public void selectRoom(RoomData room) {
        selectedRoom.setValue(room);
    }
}


