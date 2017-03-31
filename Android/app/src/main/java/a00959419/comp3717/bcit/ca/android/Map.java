package a00959419.comp3717.bcit.ca.android;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Andrew on 3/22/2017.
 */

public class Map {
    ScreenPlay playScreen;
    float maxX;
    float maxY;
    float minX;
    float minY;
    private JSONArray buildings;
    private ArrayList<Rect> rects = new ArrayList<>();
    private ArrayList<Rect> circles = new ArrayList<>();

    private JSONArray trees;

    private static final int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static final int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Map(JSONArray buildings, JSONArray trees) {
        this.buildings = buildings;
        this.trees = trees;
        try {
            minMax(buildings);
            //minMax(trees);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }

/*    public void display(Canvas canvas, Paint paint) throws JSONException {
        for (int i = 0; i < buildings.length(); i++) {
            // JSONArray lineString = jsonArray.get(i);
            JSONArray lineString = buildings.getJSONObject(i).getJSONArray("coordinates");

            float[] points = new float[lineString.length() * 2];

            for (int j = 0; j < lineString.length(); j++) {
                points[2 * j] = (float) (((JSONArray) lineString.get(j)).getDouble(0) - minX) * 2;
                points[2 * j + 1] = (float) (((JSONArray) lineString.get(j)).getDouble(1) - minY) * 2;

                //System.out.println(points[2*j]);
            }
            //canvas.drawRect();
            canvas.drawLines(points, paint);
        }
    }*/

    public void displayBuildings(Canvas canvas, Paint paint) throws JSONException {
        for (int i = 0; i < buildings.length(); i++) {
            // JSONArray lineString = jsonArray.get(i);
            JSONArray lineString = buildings.getJSONObject(i).getJSONArray("coordinates");

            float[] points = new float[8];

            for (int j = 0; j < 4; j++) {
                points[2 * j] = (float) (((JSONArray) lineString.get(j)).getDouble(0) - minX) * 3 + 100;
                points[2 * j + 1] = (float) (((JSONArray) lineString.get(j)).getDouble(1) - minY) * 3 + 100;

                //System.out.println(points[2*j]);
            }

            Rect rect = createRect(points);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GRAY);
            canvas.drawRect(rect, paint);

            rects.add(rect);
        }
    }

    public void displayTrees(Canvas canvas, Paint paint) throws JSONException {
        for (int i = 0; i < trees.length(); i++) {
            // JSONArray lineString = jsonArray.get(i);
            JSONArray lineString = trees.getJSONObject(i).getJSONArray("coordinates");

            float[] points = new float[3];

            for (int j = 0; j < 1; j++) {
                points[0] = (float) (((JSONArray) lineString.get(j)).getDouble(0) - minX) * 3 + 100;
                points[1] = (float) (((JSONArray) lineString.get(j)).getDouble(1) - minY) * 3 + 100;
                points[2] = 10;
                //System.out.println(points[2*j]);
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GREEN);
            canvas.drawCircle(points[0], points[1], points[2], paint);

            Rect circle = new Rect((int) points[0] - (int) points[2], (int) points[1] -
                    (int) points[2], (int) points[0] + (int) points[2], (int) points[1] +
                    (int) points[2]);

            circles.add(circle);
            //rects.add(rect);
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

    public void minMax(JSONArray jsonArray) throws JSONException {
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
        }
    }

    public ArrayList<Rect> getRects() {
        return rects;
    }

    public ArrayList<Rect> getCircles() {
        return circles;
    }
}
