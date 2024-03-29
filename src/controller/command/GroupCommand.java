package controller.command;

import model.persistence.CommandHistory;
import model.persistence.UndoCommandHistory;
import model.interfaces.IShape;
import model.persistence.ShapeGroup;
import model.persistence.Groups;

import java.util.HashMap;
import java.util.List;

public class GroupCommand implements ICommand, IUndoRedoCommand {
    private static final String commandName = "Group";
    private List<IShape> groupedShapes;
    private List<IShape> shapes;
    private HashMap<IShape, ShapeGroup> shapeGroupHashMap;

    // This groups all selected shapes together
    public GroupCommand(List<IShape> shapes) {
        this.shapes = shapes;
    }

    // This groups shape in their respective group.
    public GroupCommand(HashMap<IShape, ShapeGroup> shapeGroupHashMap) {
        this.shapeGroupHashMap = shapeGroupHashMap;
    }

    @Override
    public void execute() {
        // Group all the selected shapes together if not mapping to specific group
        if (shapeGroupHashMap == null) {
            for (IShape shape: shapes) {
                System.out.println("Grouping " +shape.getShapeType());
            }
            ShapeGroup newShapeGroup = new ShapeGroup(shapes);
            // Add the new group to groups
            Groups.add(newShapeGroup);

        }
        // Check if we're grouping items back together
        else {
            for(IShape shape: shapeGroupHashMap.keySet()) {
                System.out.println("Grouping "+shape.getShapeType());
                ShapeGroup shapeGroup = shapeGroupHashMap.get(shape);
                shapeGroup.add(shape);
            }
        }
        // Store the selected shapes
        storeGroupedShapes(shapes);

        if (!UndoCommandHistory.contains(this)) {
            CommandHistory.add(this);
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    private void storeGroupedShapes(List<IShape> groupedShapes) {
        this.groupedShapes = groupedShapes;
    }

    List<IShape> getGroupedShapes() {
        return groupedShapes;
    }

    public void undo() {
        System.out.println("Undoing group");
        // Get grouped shapes.
        List<IShape> groupedShapes = this.getGroupedShapes();
        // Now ungroup each shape
        UngroupCommand ungroupCommand = new UngroupCommand(groupedShapes);
        UndoCommandHistory.add(ungroupCommand);
        ungroupCommand.execute();
    }

    public void redo() {
        System.out.println("Redoing group");
        // Get grouped shapes.
        List<IShape> groupedShapes = this.getGroupedShapes();
        // Now ungroup each shape
        UngroupCommand ungroupCommand = new UngroupCommand(groupedShapes);
        CommandHistory.add(ungroupCommand);
        ungroupCommand.execute();
    }

}
