package localization.grid;

import console.LoggerProvider;
import junit.framework.Assert;
import localization.maze.Direction;
import localization.maze.DirectionalPoint;
import localization.maze.MazePoint;
import org.junit.Test;

import java.util.Arrays;

/**
 * Date: 8/17/12
 */
public class GridWorldTest {
    @Test
    public void testPositivePositive(){
        MazePoint goal = new MazePoint(5, 5);
        GridWorld gridWorld = new DynamicGridWorld(goal);

        int[][] grid = checkWorldDimentions(gridWorld, 5, 5);

        Assert.assertEquals("Value at [4][4]", GridWorld.GOAL, grid[4][4]);
        Assert.assertEquals("Robot location", new MazePoint(0, 0), gridWorld.getRobotLocation());
    }

    @Test
    public void testPositiveNegative(){
        MazePoint goal = new MazePoint(5, -5);
        GridWorld gridWorld = new DynamicGridWorld(goal);

        int[][] grid = checkWorldDimentions(gridWorld, 5, 5);
        printGrid(grid, gridWorld.getRobotLocation());

        Assert.assertEquals("Value at [0][4]", GridWorld.GOAL, grid[0][4]);
        Assert.assertEquals("Robot location", new MazePoint(0, 4), gridWorld.getRobotLocation());
    }

    @Test
    public void testNegativePositive(){
        MazePoint goal = new MazePoint(-5, 5);
        GridWorld gridWorld = new DynamicGridWorld(goal);

        int[][] grid = checkWorldDimentions(gridWorld, 5, 5);

        Assert.assertEquals("Value at [4][0]", GridWorld.GOAL, grid[4][0]);
        Assert.assertEquals("Robot location", new MazePoint(4, 0), gridWorld.getRobotLocation());
    }

    @Test
    public void testNegativeNegative(){
        MazePoint goal = new MazePoint(-5, -5);
        GridWorld gridWorld = new DynamicGridWorld(goal);

        int[][] grid = checkWorldDimentions(gridWorld, 5, 5);

        Assert.assertEquals("Value at [0][0]", GridWorld.GOAL, grid[0][0]);
        Assert.assertEquals("Robot location", new MazePoint(4, 4), gridWorld.getRobotLocation());
    }

    @Test
    public void testLong(){
        MazePoint goal = new MazePoint(8, 2);
        GridWorld gridWorld = new DynamicGridWorld(goal);

        checkWorldDimentions(gridWorld, 2, 8);
    }

    @Test
    public void testWide(){
        MazePoint goal = new MazePoint(2, 8);
        GridWorld gridWorld = new DynamicGridWorld(goal);

        checkWorldDimentions(gridWorld, 8, 2);
    }

    //-----------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    public void testIncreaseInside(){
        GridWorld world = new DynamicGridWorld(new MazePoint(5, 5));

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 3, 3);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(1);
        positionInGrid.setRightMeasure(1);
        positionInGrid.setFrontMeasure(1);
        positionInGrid.setRearMeasure(1);

        world.increaseGridAndUpdateWalls(positionInGrid);

        int[][] grid = checkWorldDimentions(world, 5, 5);
        Assert.assertEquals("Value at [4][4]", GridWorld.GOAL, grid[4][4]);
    }

    @Test
    public void testIncreaseOnLeftSide(){
        GridWorld world = new DynamicGridWorld(new MazePoint(5, 5));

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 2, 4);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(2);
        positionInGrid.setRightMeasure(1);
        positionInGrid.setFrontMeasure(1);
        positionInGrid.setRearMeasure(1);

        world.increaseGridAndUpdateWalls(positionInGrid);

        int[][] grid = checkWorldDimentions(world, 6, 5);
        Assert.assertEquals("Value at [4][4]", GridWorld.GOAL, grid[4][4]);
    }

    @Test
    public void testIncreaseOnFrontSide(){
        GridWorld world = new DynamicGridWorld(new MazePoint(5, 5));

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 3, 3);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(1);
        positionInGrid.setRightMeasure(1);
        positionInGrid.setFrontMeasure(2);
        positionInGrid.setRearMeasure(1);

        world.increaseGridAndUpdateWalls(positionInGrid);

        int[][] grid = checkWorldDimentions(world, 6, 5);
        Assert.assertEquals("Value at [4][4]", GridWorld.GOAL, grid[4][4]);
    }

    @Test
    public void testIncreaseLeft(){
        GridWorld world = new DynamicGridWorld(new MazePoint(5, 5));
        printGrid(world.getGrid(), world.getRobotLocation());

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 1, 4);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(4);
        positionInGrid.setRightMeasure(1);
        positionInGrid.setFrontMeasure(1);
        positionInGrid.setRearMeasure(1);

        world.increaseGridAndUpdateWalls(positionInGrid);

        int[][] grid = checkWorldDimentions(world, 6, 8);
        printGrid(grid, world.getRobotLocation());
        Assert.assertEquals("Value at [4][7]", GridWorld.GOAL, grid[4][7]);

        Assert.assertEquals("Value at [4][0]", GridWorld.WALL, grid[4][0]);
        Assert.assertEquals("Value at [4][5]", GridWorld.WALL, grid[4][5]);
        Assert.assertEquals("Value at [5][4]", GridWorld.WALL, grid[5][4]);
        Assert.assertEquals("Value at [3][4]", GridWorld.WALL, grid[3][4]);
    }

    @Test
    public void testIncreaseRight(){
        GridWorld world = new DynamicGridWorld(new MazePoint(5, 5));

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 1, 1);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(1);
        positionInGrid.setRightMeasure(7);
        positionInGrid.setFrontMeasure(1);
        positionInGrid.setRearMeasure(1);

        world.increaseGridAndUpdateWalls(positionInGrid);

        int[][] grid = checkWorldDimentions(world, 5, 9);
        Assert.assertEquals("Value at [4][4]", GridWorld.GOAL, grid[4][4]);
    }

    @Test
    public void testIncreaseTop(){
        GridWorld world = new DynamicGridWorld(new MazePoint(5, 5));

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 3, 3);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(1);
        positionInGrid.setRightMeasure(1);
        positionInGrid.setFrontMeasure(5);
        positionInGrid.setRearMeasure(1);

        world.increaseGridAndUpdateWalls(positionInGrid);

        int[][] grid = checkWorldDimentions(world, 9, 5);
        Assert.assertEquals("Value at [4][4]", GridWorld.GOAL, grid[4][4]);
    }

    @Test
    public void testIncreaseBottom(){
        GridWorld world = new DynamicGridWorld(new MazePoint(5, 5));
        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 3, 3);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(1);
        positionInGrid.setRightMeasure(1);
        positionInGrid.setFrontMeasure(1);
        positionInGrid.setRearMeasure(5);

        world.increaseGridAndUpdateWalls(positionInGrid);

        int[][] grid = checkWorldDimentions(world, 7, 5);
        Assert.assertEquals("Value at [4][4]", GridWorld.GOAL, grid[6][4]);
    }

    @Test
    public void testIncreaseBottomWithWalls(){
        GridWorld world = new DynamicGridWorld(new MazePoint(5, 5));
        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 3, 2);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(2);
        positionInGrid.setRightMeasure(2);
        positionInGrid.setFrontMeasure(0);
        positionInGrid.setRearMeasure(0);

        world.increaseGridAndUpdateWalls(positionInGrid);
        System.out.println(world.printGrid());

        PositionInGrid positionInGrid1 = new PositionInGrid();
        DirectionalPoint robotLocation = world.getRobotLocation();
        robotLocation.setY(robotLocation.getY() + 1);
        positionInGrid1.setCurrentPosition(robotLocation);
        positionInGrid1.setLeftMeasure(4);
        positionInGrid1.setRightMeasure(2);
        positionInGrid1.setFrontMeasure(0);
        positionInGrid1.setRearMeasure(0);

        world.increaseGridAndUpdateWalls(positionInGrid1);
        System.out.println(world.printGrid());

        positionInGrid1 = new PositionInGrid();
        robotLocation = world.getRobotLocation();
        robotLocation.setY(robotLocation.getY() + 1);
        positionInGrid1.setCurrentPosition(robotLocation);
        positionInGrid1.setLeftMeasure(4);
        positionInGrid1.setRightMeasure(2);
        positionInGrid1.setFrontMeasure(0);
        positionInGrid1.setRearMeasure(0);

        world.increaseGridAndUpdateWalls(positionInGrid1);
        System.out.println(world.printGrid());

        //int[][] grid = checkWorldDimentions(world, 7, 5);
        //Assert.assertEquals("Value at [4][4]", GridWorld.GOAL, grid[6][4]);
    }

    @Test
    public void testIncreaseBottomAndLeft(){
        GridWorld world = new DynamicGridWorld(new MazePoint(5, 5));

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 3, 3);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(5);
        positionInGrid.setRightMeasure(5);
        positionInGrid.setFrontMeasure(5);
        positionInGrid.setRearMeasure(5);

        world.increaseGridAndUpdateWalls(positionInGrid);

        int[][] grid = checkWorldDimentions(world, 11, 11);
        Assert.assertEquals("Value at [4][4]", GridWorld.GOAL, grid[6][6]);
    }

    @Test
    public void testNoIncrease(){
        GridWorld world = new DynamicGridWorld(new MazePoint(5, 5));

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 3, 3);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);

        world.increaseGridAndUpdateWalls(positionInGrid);

        int[][] grid = checkWorldDimentions(world, 5, 5);
        Assert.assertEquals("Value at [4][4]", GridWorld.GOAL, grid[4][4]);
    }

    private int[][] checkWorldDimentions(GridWorld world, int length, int width) {
        int[][] grid = world.getGrid();
        Assert.assertEquals("Length", length, grid.length);
        Assert.assertEquals("Width", width, grid[0].length);

        return grid;
    }

    public static void printGrid(int[][] grid, MazePoint robotLocation){
        for (int i=grid.length - 1; i>=0; i--){
            for (int j=0; j<grid[i].length; j++){
                if (j == robotLocation.getX() && i == robotLocation.getY()){
                    System.out.print("R ");
                } else {
                    System.out.print(grid[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("------------");
    }

    //-----------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------

    @Test
     public void testActualize(){
        MazePoint goal = new MazePoint(5, -5);
        DynamicGridWorld gridWorld = new DynamicGridWorld(goal);

        int[][] grid = checkWorldDimentions(gridWorld, 5, 5);
        printGrid(grid, gridWorld.getRobotLocation());

        gridWorld.updateGrid();

        grid = checkWorldDimentions(gridWorld, 5, 5);

        printGrid(grid, gridWorld.getRobotLocation());

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 3, 3);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        positionInGrid.setLeftMeasure(1);

        actualizeAndPrint(gridWorld, positionInGrid);

    }

    private void actualizeAndPrint(DynamicGridWorld gridWorld, PositionInGrid positionInGrid) {
        int[][] grid;
        gridWorld.actualize(positionInGrid);

        grid = checkWorldDimentions(gridWorld, 5, 5);
        printGrid(grid, gridWorld.getRobotLocation());
    }

    @Test
    public void testCosts(){
        MazePoint goal = new MazePoint(5, -5);
        DynamicGridWorld gridWorld = new DynamicGridWorld(goal);

        gridWorld.updateGrid();
        int[][] grid = checkWorldDimentions(gridWorld, 5, 5);
        printGrid(grid, gridWorld.getRobotLocation());
        int[][] expectedGrid = new int[5][];
        expectedGrid[0] = new int[]{4, 3, 2, 1, 0};
        expectedGrid[1] = new int[]{5, 4, 3, 2, 1};
        expectedGrid[2] = new int[]{6, 5, 4, 3, 2};
        expectedGrid[3] = new int[]{7, 6, 5, 4, 3};
        expectedGrid[4] = new int[]{8, 7, 6, 5, 4};

        for (int i=0; i<5; i++){
            Assert.assertTrue("Array[" + i + "] after update", Arrays.equals(expectedGrid[i], grid[i]));
        }
    }

    @Test
    public void testGreatWallCosts(){
        MazePoint goal = new MazePoint(5, -5);
        DynamicGridWorld gridWorld = new DynamicGridWorld(goal);

        gridWorld.updateGrid();
        checkWorldDimentions(gridWorld, 5, 5);

        for (int i = 1; i <= 4; i++){
            DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, i, 0);
            PositionInGrid positionInGrid = new PositionInGrid();
            positionInGrid.setCurrentPosition(mazePoint);
            positionInGrid.setFrontMeasure(1);
            gridWorld.actualize(positionInGrid);
        }

        for (int i = 0; i < 4; i++){
            DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, i, 2);
            PositionInGrid positionInGrid = new PositionInGrid();
            positionInGrid.setCurrentPosition(mazePoint);
            positionInGrid.setFrontMeasure(1);
            gridWorld.actualize(positionInGrid);
        }


        printGrid(gridWorld.getGrid(), gridWorld.getRobotLocation());

        int[][] expectedGrid = new int[5][];
        expectedGrid[0] = new int[]{4, 3, 2, 1, 0};
        expectedGrid[1] = new int[]{5, -1, -1, -1, -1};
        expectedGrid[2] = new int[]{6, 7, 8, 9, 10};
        expectedGrid[3] = new int[]{-1, -1, -1, -1, 11};
        expectedGrid[4] = new int[]{16, 15, 14, 13, 12};

        printGrid(expectedGrid, gridWorld.getRobotLocation());

        for (int i=0; i<5; i++){
            Assert.assertTrue("Array[" + i + "] after update", Arrays.equals(expectedGrid[i], gridWorld.getGrid()[i]));
        }
    }

    @Test
    public void testUnreachableSpace(){
        MazePoint goal = new MazePoint(5, -5);
        DynamicGridWorld gridWorld = new DynamicGridWorld(goal);

        gridWorld.updateGrid();
        checkWorldDimentions(gridWorld, 5, 5);

        for (int i = 0; i < 3; i++){
            DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, i, 1);
            PositionInGrid positionInGrid = new PositionInGrid();
            positionInGrid.setCurrentPosition(mazePoint);
            positionInGrid.setFrontMeasure(1);
            gridWorld.actualize(positionInGrid);
        }

        for (int i = 0; i < 2; i++){
            DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 3, (i));
            PositionInGrid positionInGrid = new PositionInGrid();
            positionInGrid.setCurrentPosition(mazePoint);
            positionInGrid.setLeftMeasure(1);
            gridWorld.actualize(positionInGrid);
        }


        printGrid(gridWorld.getGrid(), gridWorld.getRobotLocation());

        int[][] expectedGrid = new int[5][];
        expectedGrid[0] = new int[]{-2, -2, -1, 1, 0};
        expectedGrid[1] = new int[]{-2, -2, -1, 2, 1};
        expectedGrid[2] = new int[]{-1, -1, -1, 3, 2};
        expectedGrid[3] = new int[]{7, 6, 5, 4, 3};
        expectedGrid[4] = new int[]{8, 7, 6, 5, 4};

        printGrid(expectedGrid, gridWorld.getRobotLocation());

        for (int i=0; i<5; i++){
            Assert.assertTrue("Array[" + i + "] after update", Arrays.equals(expectedGrid[i], gridWorld.getGrid()[i]));
        }
    }

    @Test
    public void testWay(){
        MazePoint goal = new MazePoint(5, 5);
        DynamicGridWorld gridWorld = new DynamicGridWorld(goal);

        gridWorld.updateGrid();
        int[][] grid = checkWorldDimentions(gridWorld, 5, 5);
        printGrid(grid, gridWorld.getRobotLocation());

        System.out.print(gridWorld.findWay());
    }

    @Test
    public void testSnakyWay(){
        MazePoint goal = new MazePoint(5, 5);
        DynamicGridWorld gridWorld = new DynamicGridWorld(goal);

        gridWorld.updateGrid();
        checkWorldDimentions(gridWorld, 5, 5);

        for (int i = 0; i < 4; i++){
            DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, i, 0);
            PositionInGrid positionInGrid = new PositionInGrid();
            positionInGrid.setCurrentPosition(mazePoint);
            positionInGrid.setFrontMeasure(1);
            gridWorld.actualize(positionInGrid);
        }

        for (int i = 1; i <= 4; i++){
            DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, i, 2);
            PositionInGrid positionInGrid = new PositionInGrid();
            positionInGrid.setCurrentPosition(mazePoint);
            positionInGrid.setFrontMeasure(1);
            gridWorld.actualize(positionInGrid);
        }

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 0, 0);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        gridWorld.actualize(positionInGrid);


        printGrid(gridWorld.getGrid(), gridWorld.getRobotLocation());

        System.out.print(gridWorld.findWay());
    }

    @Test
    public void testNoWay(){
        LoggerProvider.initProvider(true);
        LoggerProvider.getProvider().callback(true);

        MazePoint goal = new MazePoint(5, 5);
        DynamicGridWorld gridWorld = new DynamicGridWorld(goal);

        gridWorld.updateGrid();
        checkWorldDimentions(gridWorld, 5, 5);

        for (int i = 0; i < 5; i++){
            DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, i, 0);
            PositionInGrid positionInGrid = new PositionInGrid();
            positionInGrid.setCurrentPosition(mazePoint);
            positionInGrid.setFrontMeasure(1);
            gridWorld.actualize(positionInGrid);
        }

        DirectionalPoint mazePoint = new DirectionalPoint(Direction.FRONT, 0, 0);
        PositionInGrid positionInGrid = new PositionInGrid();
        positionInGrid.setCurrentPosition(mazePoint);
        gridWorld.actualize(positionInGrid);


        printGrid(gridWorld.getGrid(), gridWorld.getRobotLocation());

        System.out.print(gridWorld.findWay());
    }
}
