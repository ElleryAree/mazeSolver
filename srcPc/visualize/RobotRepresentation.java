package visualize;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

public class RobotRepresentation extends JPanel{

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g.create();
        try {
            Rectangle back_rect = new Rectangle(50, 50, getWidth(),
                    getHeight());
            graphics.setColor(Color.WHITE);
            graphics.fill(back_rect);

            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            Point[] current = getTriangle();
            drawRegion(graphics, current);

        } finally {
            graphics.dispose();
        }
    }

    private Point[] getTriangle() {
        return new Point[]{new Point(0, 500), new Point(250, 0), new Point(50, 500)};
    }

    private void drawRegion(Graphics2D graphics, Point[] points) {
        graphics.setColor(Color.CYAN);
        Area area = new Area(getPath(points));
        graphics.fill(area);
        graphics.draw(area);
    }

    private GeneralPath getPath(Point[] points) {
        GeneralPath path = new GeneralPath();
//        Point current_screen_point = calculateScreenPoint(points[0]);
        Point current_screen_point = points[0];
        path.moveTo(current_screen_point.x, current_screen_point.y);
        for (int point_num = 1; point_num < points.length; point_num++) {
//            current_screen_point = calculateScreenPoint(points[point_num]);
            current_screen_point = points[point_num];
            path.lineTo(current_screen_point.x, current_screen_point.y);
        }

//        path.closePath();
        return path;
    }

    public Point calculateScreenPoint(Point image_point) {
        float h_proportion = (float) image_point.x;
        float v_proportion = (float) image_point.y;
        float image_width_in_panel = 50;
        float image_height_in_panel = 50;

        Point on_screen_point = new Point(0, 0);
        on_screen_point.x = Math.round(h_proportion * image_width_in_panel);
        on_screen_point.y = Math.round(v_proportion * image_height_in_panel);
        return on_screen_point;
    }
}
