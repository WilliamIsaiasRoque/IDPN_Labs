package com.example.lab6.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.lab6.model.RoomData;

import java.util.ArrayList;
import java.util.List;

public class CustomPlanView extends View {

    private List<RoomData> rooms = new ArrayList<>();
    private OnRoomClickListener listener;

    private Paint paintFill;
    private Paint paintBorder;
    private Paint paintText;
    private Paint paintLabelBg;
    private Paint paintLabelText;

    private float scaleFactor = 1f;
    private float offsetX = 0f;
    private float offsetY = 0f;
    private float sourceWidth = 1200f;
    private float sourceHeight = 800f;

    public interface OnRoomClickListener {
        void onRoomClick(RoomData room);
    }

    public CustomPlanView(Context context) {
        super(context);
        init();
    }

    public CustomPlanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setARGB(255, 255, 224, 204);

        paintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setStrokeWidth(4f);
        paintBorder.setARGB(255, 0, 0, 0);

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTextSize(36f);
        paintText.setARGB(255, 0, 0, 0);

        paintLabelBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLabelBg.setStyle(Paint.Style.FILL);
        paintLabelBg.setARGB(255, 255, 160, 0);

        paintLabelText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLabelText.setTextSize(30f);
        paintLabelText.setARGB(255, 0, 0, 0);
        paintLabelText.setTextAlign(Paint.Align.CENTER);
    }

    public void setRooms(List<RoomData> rooms) {
        this.rooms = rooms;
        invalidate();
    }

    public void setOnRoomClickListener(OnRoomClickListener l) {
        this.listener = l;
    }

    private void computeScale(int w, int h) {
        float sx = (float) w / sourceWidth;
        float sy = (float) h / sourceHeight;
        scaleFactor = Math.min(sx, sy);
        // centra el plano
        offsetX = (w - sourceWidth * scaleFactor) / 2f;
        offsetY = (h - sourceHeight * scaleFactor) / 2f;
    }

    private PointF transform(PointF p) {
        return new PointF(p.x * scaleFactor + offsetX, p.y * scaleFactor + offsetY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        computeScale(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rooms == null) return;

        for (int i = 0; i < rooms.size(); i++) {
            RoomData r = rooms.get(i);
            List<PointF> verts = r.getVertices();
            if (verts == null || verts.size() < 3) continue;

            Path path = new Path();
            PointF p0 = transform(verts.get(0));
            path.moveTo(p0.x, p0.y);
            for (int j = 1; j < verts.size(); j++) {
                PointF pj = transform(verts.get(j));
                path.lineTo(pj.x, pj.y);
            }
            path.close();

            canvas.drawPath(path, paintFill);
            canvas.drawPath(path, paintBorder);

            float cx = p0.x;
            float cy = p0.y;
            float radius = 26f;
            canvas.drawCircle(cx, cy, radius, paintLabelBg);
            String label = String.valueOf(i + 1);
            float textY = cy - ((paintLabelText.descent() + paintLabelText.ascent()) / 2);
            canvas.drawText(label, cx, textY, paintLabelText);

            canvas.drawText(r.getName(), p0.x + 40f, p0.y + 30f, paintText);
        }
    }

    private boolean pointInPolygon(float x, float y, List<PointF> polygon) {
        boolean inside = false;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            PointF pi = transform(polygon.get(i));
            PointF pj = transform(polygon.get(j));
            boolean intersect = ((pi.y > y) != (pj.y > y)) &&
                    (x < (pj.x - pi.x) * (y - pi.y) / (pj.y - pi.y) + pi.x);
            if (intersect) inside = !inside;
        }
        return inside;
    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && rooms != null) {
            float x = event.getX();
            float y = event.getY();
            for (RoomData r : rooms) {
                if (pointInPolygon(x, y, r.getVertices())) {
                    if (listener != null) listener.onRoomClick(r);
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
