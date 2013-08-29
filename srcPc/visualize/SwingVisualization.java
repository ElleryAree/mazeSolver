package visualize;

import localization.grid.GridWorld;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SwingVisualization extends JPanel implements UpdatableView{
    private GridLayout gridLayout;
    private JComponent gridComponent;
    private JLabel messageLabel;
    private JLabel serviceMessageLabel;
    private static JFrame frame;

    private JComponent[][] displayedGrid;

    public static Color getPaint(int code){
        switch (code){
            case GridWorld.WALL: return Color.BLACK;
            case GridWorld.EMPTY: return Color.WHITE;
            case GridWorld.GOAL: return Color.GREEN;
            default: return Color.YELLOW;
        }
    }

    public void updateWorld(int[][] grid){
        gridLayout.setRows(grid.length);
        gridLayout.setColumns(grid[0].length);
        gridComponent.removeAll();

        displayedGrid = new JComponent[grid.length][grid[0].length];

        int cellHeight = 50;
        int cellWidth = 50;
        int height = 50 + cellHeight * grid[0].length;
        int width = cellWidth * grid.length;
        if (width < 300) width = 300;

        if (height > 1000) {
            height = 1000;
            cellHeight = height / grid[0].length;
        }
        if (width > 1000) {
            width = 1000;
            cellWidth = width / grid.length;
        }

        for (int i=grid.length-1; i>=0; i--) {
            for (int j=0; j<grid[i].length; j++) {
                JComponent rect = new JPanel();
                rect.setSize(cellWidth, cellHeight);
                rect.setBackground(getPaint(grid[i][j]));
                displayedGrid[i][j] = rect;

                gridComponent.add(rect);
            }
        }

        frame.setSize(width, height);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void updateRobot(int x, int y, int rotation) {
        String direction;

        if (rotation == Direction.FRONT.getDegrees()) direction = "^";
        else if (rotation == Direction.BACK.getDegrees()) direction = "v";
        else if (rotation == Direction.LEFT.getDegrees()) direction = "<";
        else direction = ">";

        JComponent representation = new JLabel(direction);
        displayedGrid[y][x].add(representation);
        displayedGrid[y][x].setBackground(Color.CYAN);

        this.revalidate();
        this.repaint();
    }

    @Override
    public void updateRoute(List<DirectionalPoint> points) {
        int i=0;
        for (DirectionalPoint point : points){
            String caption = (i++) + ": ";
            switch (point.getDirection()){
                case LEFT:
                    caption += "<";
                    break;
                case RIGHT:
                    caption += ">";
                    break;
                case FRONT:
                    caption += "^";
                    break;
                default:
                    caption += "v";
            }

            JComponent representation = new JLabel(caption);
            displayedGrid[point.getY()][point.getX()].add(representation);
        }

        this.revalidate();
        this.repaint();
    }

    public void showMessage(String message){
        messageLabel.setText(message);

        this.revalidate();
        this.repaint();
    }

    public void showServiceMessage(String message){
        serviceMessageLabel.setText(message);

        this.revalidate();
        this.repaint();
    }

    public SwingVisualization(final Pausable pausable) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //Create the label.
        messageLabel = new JLabel("Grid vizualization", JLabel.CENTER);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        serviceMessageLabel = new JLabel("", JLabel.CENTER);
        serviceMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        gridComponent = new JPanel();
        gridLayout = new GridLayout();
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);
        gridComponent.setLayout(gridLayout);

        FlowLayout buttonsLayout = new FlowLayout();
        buttonsLayout.setHgap(10);
        buttonsLayout.setVgap(10);
        JComponent buttonsComponent = new JPanel();
        buttonsComponent.setLayout(buttonsLayout);

        JButton buttonRunPause = new JButton("Pause/Run");
        if (pausable != null){
            buttonRunPause.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                        pausable.pause();
                        showMessage("Paused");
                }
            });
        } else {
            buttonRunPause.setEnabled(false);
        }
        JButton buttonClear = new JButton("Clear");
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gridComponent.removeAll();
            }
        });
        buttonsComponent.add(buttonRunPause);
        buttonsComponent.add(buttonClear);

        //Put everything together.
        add(messageLabel);
        add(serviceMessageLabel);
        add(gridComponent);
        add(buttonsComponent);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static SwingVisualization startVisualizer(Pausable pausable) {
        //Create and set up the window.
        frame = new JFrame("SwingVisualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);
        SwingVisualization animator = new SwingVisualization(pausable);

        //Add content to the window.
        frame.add(animator, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        return animator;
    }

    public static void main(String[] args) {
        SwingVisualization test = startVisualizer(null);

        int[][] grid = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        test.updateWorld(grid);
        test.updateRobot(1, 2, 0);
    }
}