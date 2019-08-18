package controller;

import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.TransformColor;
import view.interfaces.PaintCanvasBase;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;


public class OutlineRectangleStrategy implements IOutlineStrategy {
    private final TransformColor transformColor = new TransformColor();

    @Override
    public void outline(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape, IApplicationState applicationState) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[]{9}, 0);
        graphics2d.setStroke(stroke);
        graphics2d.setColor(transformColor.transform(applicationState.getActivePrimaryColor()));
        graphics2d.drawRect(startPoint.getX()-5, startPoint.getY()-5, endPoint.getX()-startPoint.getX()+10,endPoint.getY() - startPoint.getY()+10);
    }
}
