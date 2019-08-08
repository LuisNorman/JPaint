package model.interfaces;

import model.ShapeColor;
import model.ShapeShadingType;
import model.ShapeType;
import view.interfaces.PaintCanvasBase;
import controller.Point;
import java.awt.Color;


import model.persistence.Location;

public interface IShape {
    void setShapeName(String shapeName);
    String getShapeName();

    void switchToSecondaryColor();

    void setPrimaryColor(ShapeColor shapeColor);
    Color getPrimaryColor();

    void setSecondaryColor(ShapeColor shapeColor);
    Color getSecondaryColor();

    void setShapeShadingType(ShapeShadingType shapeShadingType);
    ShapeShadingType getShapeShadingType();

    void setLocation(Point startPoint, Point endPoint);
    Location getLocation();

    void loadGraphics(PaintCanvasBase paintCanvasBase);

    void build(IApplicationState applicationState);

    void setShapeType(ShapeType shapeType);

    ShapeType getShapeType();

    void setWidth(int width);

    int getWidth();

    void setHeight(int height);

    int getHeight();
}
