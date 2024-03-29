package controller.command;

import model.persistence.CommandHistory;
import model.persistence.UndoCommandHistory;
import model.interfaces.IShape;
import model.persistence.ShapeGroup;
import model.persistence.Groups;
import view.gui.PaintCanvas;
import view.interfaces.PaintCanvasBase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class UngroupCommand implements ICommand, IUndoRedoCommand {
    private HashMap<IShape, ShapeGroup> ungroupedShapes;
    private List<IShape> shapesToUngroup;
    private final static String commandName = "Ungroup";

    public UngroupCommand(List<IShape> shapesToUngroup) {
        this.shapesToUngroup = shapesToUngroup;
    }

    @Override
    // Ungroup the selected rectangles
    public void execute() {
        HashMap<IShape, ShapeGroup> temp = new HashMap<>();
        List<IShape> tempShapes = new LinkedList<>();
        // Create temp list of shapes to ungroup
        tempShapes.addAll(shapesToUngroup);

        List<ShapeGroup> groups = Groups.getGroups();

//      Loop each shape then loop each group checking if the shape is in the group
        for (int i=0; i<tempShapes.size(); i++) {
            IShape currentShape = tempShapes.get(i);
            for (int j=0; j<groups.size(); j++) {
                ShapeGroup currentGroup = groups.get(j);
                if (currentGroup.contains(currentShape)) {
                    temp.put(currentShape, currentGroup);
                    currentGroup.delete(currentShape);
                    System.out.println("Ungrouping "+currentShape.getShapeType());
                    break;
                }
            }
        }
        storeUngroupedShapes(temp);
        if (!UndoCommandHistory.contains(this)) {
            CommandHistory.add(this);
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    private void storeUngroupedShapes(HashMap<IShape, ShapeGroup> ungroupedShapes) {
        this.ungroupedShapes = ungroupedShapes;
    }

    HashMap<IShape, ShapeGroup> getUngroupedShapes() {
        return ungroupedShapes;
    }

    public void undo() {
        System.out.println("Undoing ungroup");
        // Get ungrouped shapes and the group it belonged to;
        HashMap<IShape, ShapeGroup> ungroupedShapes = this.getUngroupedShapes();
        GroupCommand groupCommand = new GroupCommand(ungroupedShapes);
        UndoCommandHistory.add(groupCommand);
        groupCommand.execute();
    }

    public void redo() {
        System.out.println("Redoing ungroup");
        // Get ungrouped shapes and the group it belonged to;
        HashMap<IShape, ShapeGroup> ungroupedShapes = this.getUngroupedShapes();
        GroupCommand groupCommand = new GroupCommand(ungroupedShapes);
        CommandHistory.add(groupCommand);
        groupCommand.execute();
    }
}
