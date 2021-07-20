package com.fogdestination.yourmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Circle;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CircleMapObject;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;


public class MapActivity extends AppCompatActivity {

    private MapView mapview;
    private MapObjectCollection objectCollection;
    private MapData mapData;
    private FrameLayout frameLayout_m;
    private Button addPlaceB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("start","create");
        try {
            MapKitFactory.setApiKey("68d326f2-2302-4ee3-869c-251aeb958678");
        }catch (AssertionError error)
        {
            Log.d("ERROR","APIKEY");
        }

//        MapKitFactory.setLocale("Russian");
        MapKitFactory.initialize(this);


        // Укажите имя activity вместо map.
        setContentView(R.layout.activity_map_view);
        init();
        checkForExtra();

    }

    void init()
    {
        mapview = (MapView)findViewById(R.id.yamap);
        mapview.getMap().move(
                new CameraPosition(new Point(59.962402, 30.320884), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);


        objectCollection = mapview.getMap().getMapObjects().addCollection();

        mapData = new MapData();
        mapData._test_init_places();
//        for(int i = 0;i<mapData.place_coords.length;i++)
//        {
//            PlacemarkMapObject placemarkMapObject =
//                    objectCollection.addPlacemark(new Point(mapData.place_coords[i].getY(),
//                            mapData.place_coords[i].getX()),ImageProvider.fromBitmap(draw_placemark("",55)));
//            placemarkMapObject.setUserData(mapData.place_coords[i].getX());
//            placemarkMapObject.addTapListener((mapObject, point) -> {
//                float x = (float)mapObject.getUserData();
//                Toast.makeText(getApplicationContext(),String.valueOf(x),Toast.LENGTH_SHORT).show();
//                return true;
//            });
//            Log.d("tag",String.valueOf(mapData.place_coords[i].getX()));
//        }

        frameLayout_m = findViewById(R.id.frame_layout);
        addPlaceB = findViewById(R.id.addPlaceB);
    }

    void checkForExtra()
    {
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            String type = extras.getString("type");
            switch (type)
            {
                case "newPlace":
                    setNewPlace(extras);
            }
        }
    }

    void setNewPlace(Bundle info)
    {

        String place_name,place_desc;
        place_name = info.getString("place_name");
        place_desc = info.getString("place_desc");

        PlaceInfo newPlace = mapData.addNewPlace(place_name,mapData.tempDot,place_desc,PlaceType.Simple);
        PlacemarkMapObject placemarkMapObject =
                objectCollection.addPlacemark(new Point(newPlace.coords.getY(),
                        newPlace.coords.getX()),ImageProvider.fromBitmap(draw_placemark("",55)));
        placemarkMapObject.setUserData(newPlace);
        placemarkMapObject.addTapListener((mapObject, point) -> {
            PlaceInfo t = (PlaceInfo) mapObject.getUserData();
            Toast.makeText(getApplicationContext(),String.valueOf(t.name),Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    public Bitmap draw_placemark(String text,int size)
    {
        int picSize = size;
        Bitmap bitmap = Bitmap.createBitmap(picSize, picSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // отрисовка плейсмарка
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.rd1));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(picSize / 2, picSize / 2, picSize / 2, paint);
        // отрисовка текста
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, picSize / 2,
                picSize / 2 - ((paint.descent() + paint.ascent()) / 2), paint);
        return bitmap;
//        mapview.getMap().getMapObjects().addPlacemark(new Point(59.962402, 30.320884), ImageProvider.fromBitmap(bitmap));

    }

    PlacemarkMapObject new_placeMark;
    Button new_placeB;
    boolean is_placing_new = false;
    boolean is_new = false;

    public void add_newPlace(View view)
    {
//        new_placeMark = objectCollection.addPlacemark(mapview.getMap().getCameraPosition().getTarget(),ImageProvider.fromResource(this,R.drawable.mark));
//        Toast.makeText(getApplicationContext()," " + new_placeMark.getZIndex(),Toast.LENGTH_SHORT).show();
//        new_placeMark.setDraggable(true);
//        new_placeMark.setZIndex(100f);
//        new_placeMark.setOpacity(0.5f);
//        CircleMapObject circleMapObject = objectCollection.addCircle(new Circle(mapview.getMap().getCameraPosition().getTarget(),100),Color.GREEN, 2, Color.RED);
//        circleMapObject.setZIndex(100.0f);
        is_new=true;
        if(is_placing_new)
        {
            addPlaceB.setText("add");
            frameLayout_m.removeView(new_placeB);
        }
        else
        {
            addPlaceB.setText("cancel");

            new_placeB = new Button(this);
            new_placeB.setText("Add");
            new_placeB.setOnClickListener(this::choosen_new_place);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(150,200);

            layoutParams.setMargins(0,0,0,75);
            frameLayout_m.addView(new_placeB,layoutParams);
            layoutParams.gravity = Gravity.CENTER;
        }
        is_placing_new=!is_placing_new;
    }

    public void choosen_new_place(View view)
    {
        Dot coords = new Dot(mapview.getMap().getCameraPosition().getTarget().getLatitude(),
                mapview.getMap().getCameraPosition().getTarget().getLongitude());
        Log.d("COORDS",String.valueOf(coords.getX()));
        mapData.tempDot = coords;
        Intent i = new Intent(MapActivity.this,AddPlaceAct.class);
        startActivityForResult(i,1);
    }


    @Override
    protected void onStop() {
        mapview.onStop();
        Log.d("start","stop");
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("start","start");
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("start","resume");
        addPlaceB.setText("add");
        frameLayout_m.removeView(new_placeB);
    }
}