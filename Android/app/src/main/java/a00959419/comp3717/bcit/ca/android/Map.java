package a00959419.comp3717.bcit.ca.android;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Andrew on 3/22/2017.
 */

public class Map {
    private float maxX;
    private float maxY;
    private float minX;
    private float minY;
    private float mapHeight;
    private float mapWidth;

    private ArrayList<Rect> buildings = new ArrayList<>();
    private ArrayList<Rect> trees = new ArrayList<>();

    private static final int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    private static final int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;

    Map(JSONArray buildings, JSONArray trees) {
        try {
            minMax(buildings);
            initBuildings(buildings);
            initTrees(trees);
            //minMax(trees);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initBuildings(JSONArray rectsJSON) throws JSONException {
        for (int i = 0; i < rectsJSON.length(); i++) {
            JSONArray lineString = rectsJSON.getJSONObject(i).getJSONArray("coordinates");

            float[] points = new float[8];

            for (int j = 0; j < 4; j++) {
                points[2 * j] = (float) ((((JSONArray) lineString.get(j)).getDouble(0) - minX) *
                                        (SCREEN_WIDTH / mapWidth) * 0.75 + SCREEN_WIDTH / 8);
                points[2 * j + 1] = (float) ((((JSONArray) lineString.get(j)).getDouble(1) - minY) *
                                        (SCREEN_HEIGHT / mapHeight) * 0.75 + SCREEN_HEIGHT / 8);
            }

            Rect rect = createRect(points);
            buildings.add(rect);
        }
    }

    private void initTrees(JSONArray pointsJSON) throws JSONException {
        for (int i = 0; i < pointsJSON.length(); i++) {
            JSONArray lineString = pointsJSON.getJSONObject(i).getJSONArray("coordinates");

            float[] points = new float[3];

            points[0] = (float) ((lineString.getDouble(0) - minX) * (SCREEN_WIDTH / mapWidth) *
                    0.75 + SCREEN_WIDTH / 8);
            points[1] = (float) ((lineString.getDouble(1) - minY) * (SCREEN_HEIGHT / mapHeight) *
                    0.75 + SCREEN_HEIGHT / 8);
            points[2] = 10;

            Rect circle = new Rect((int) points[0] - (int) points[2], (int) points[1] -
                    (int) points[2], (int) points[0] + (int) points[2], (int) points[1] +
                    (int) points[2]);

            trees.add(circle);
        }
    }

    void display(Canvas canvas, Paint paint) throws JSONException {
        displayBuildings(canvas, paint);
        displayTrees(canvas, paint);
    }

    private void displayBuildings(Canvas canvas, Paint paint) throws JSONException {
        for (Rect rect : buildings) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GRAY);
            canvas.drawRect(rect, paint);
        }
    }

    private void displayTrees(Canvas canvas, Paint paint) throws JSONException {
        for (Rect circ : trees) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GREEN);
            canvas.drawCircle(circ.centerX(), circ.centerY(), 10, paint);
        }
    }

    private Rect createRect(float[] points) {
        float MINX = Float.MAX_VALUE;
        float MINY = Float.MAX_VALUE;
        float MAXX = 0;
        float MAXY = 0;

        for (int i = 0; i < points.length; i++) {
            if (i % 2 == 0) {
                if (MINX > points[i]) {
                    MINX = points[i];
                }
                if (MAXX < points[i]) {
                    MAXX = points[i];
                }
            } else {
                if (MINY > points[i]) {
                    MINY = points[i];
                }
                if (MAXY < points[i]) {
                    MAXY = points[i];
                }
            }
        }
        return new Rect((int) MINX, (int) MINY, (int) MAXX, (int) MAXY);
    }

    private void minMax(JSONArray jsonArray) throws JSONException {
        minX = Float.MAX_VALUE;
        minY = Float.MAX_VALUE;

        for (int i = 0; i < jsonArray.length(); i++) {
            // JSONArray lineString = jsonArray.get(i);
            JSONArray lineString = jsonArray.getJSONObject(i).getJSONArray("coordinates");
            for (int j = 0; j < lineString.length(); j++) {
                JSONArray startCoordinates = (JSONArray) lineString.get(j);
                float xCur = (float) startCoordinates.getDouble(0);
                float yCur = (float) startCoordinates.getDouble(1);

                if (minX > xCur) {
                    minX = xCur;
                }
                if (maxX < xCur) {
                    maxX = xCur;
                }
                if (minY > yCur) {
                    minY = yCur;
                }
                if (maxY < yCur) {
                    maxY = yCur;
                }
            }
            mapHeight = maxY - minY;
            mapWidth = maxX - minX;
        }
    }

    public ArrayList<Rect> getBuildings() {
        return buildings;
    }

    public ArrayList<Rect> getTrees() {
        return trees;
    }
}
