package a00959419.comp3717.bcit.ca.android;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Andrew on 3/22/2017.
 */

public class Map {
    ScreenPlay playScreen;
    float maxX;
    float maxY;
    float minX;
    float minY;
    JSONArray lines;

    public Map(JSONArray lines) {
        this.lines = lines;

        try {
            minMax();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(minX + " " + minY);
    }

    public void display(Canvas canvas, Paint paint) throws JSONException {
        for (int i = 0; i < lines.length(); i++) {
            // JSONArray lineString = jsonArray.get(i);
            JSONArray lineString = lines.getJSONObject(i).getJSONArray("coordinates");

            float[] points = new float[lineString.length() * 2];

            for (int j = 0; j < lineString.length(); j++) {
                points[2 * j] = (float) (((JSONArray) lineString.get(j)).getDouble(0) - minX)*2;
                points[2 * j + 1] = (float) (((JSONArray) lineString.get(j)).getDouble(1) - minY)*2;

                //System.out.println(points[2*j]);
            }

            canvas.drawLines(points, paint);
        }
    }

    public void minMax() throws JSONException {
        minX = Float.MAX_VALUE;
        minY = Float.MAX_VALUE;

        for (int i = 0; i < lines.length(); i++) {
            // JSONArray lineString = jsonArray.get(i);
            JSONArray lineString = lines.getJSONObject(i).getJSONArray("coordinates");
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

}
