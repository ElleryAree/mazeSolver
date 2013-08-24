package movement;

import localization.maze.MazePoint;
import org.junit.Test;

/**
 * Date: 8/19/12
 */
public class GridWorldRunnerTest {
    @Test
    public void test(){
        int[][] expectedGrid = new int[5][];
        expectedGrid[4] = new int[]{-2, -2, -1, -2, 0};
        expectedGrid[3] = new int[]{-2, -2, -1, -2, -2};
        expectedGrid[2] = new int[]{-1, -1, -1, -2, -2};
        expectedGrid[1] = new int[]{-2, -2, -2, -2, -2};
        expectedGrid[0] = new int[]{-2, -2, -2, -2, -2};

        MazePoint goal = new MazePoint(5, 5);

        MockGridMovement mockGridMovement = new MockGridMovement(expectedGrid);
        GridWorldRunner runner = new GridWorldRunner(goal, mockGridMovement);
        runner.run();
    }

    @Test
    public void test1(){
        int[][] expectedGrid = new int[5][];
        expectedGrid[4] = new int[]{-2, -2, -1, -2, 0};
        expectedGrid[3] = new int[]{-2, -2, -1, -2, -2};
        expectedGrid[2] = new int[]{-1, -1, -2, -2, -2};
        expectedGrid[1] = new int[]{-2, -2, -2, -1, -2};
        expectedGrid[0] = new int[]{-2, -2, -2, -1, -2};

        MazePoint goal = new MazePoint(5, 5);

        MockGridMovement mockGridMovement = new MockGridMovement(expectedGrid);
        GridWorldRunner runner = new GridWorldRunner(goal, mockGridMovement);
        runner.run();
    }

    @Test
    public void test2(){
        int[][] expectedGrid = new int[5][];
        expectedGrid[4] = new int[]{-2, -2, -1, -2, 0};
        expectedGrid[3] = new int[]{-2, -2, -1, -2, -2};
        expectedGrid[2] = new int[]{-1, -1, -1, -1, -1};
        expectedGrid[1] = new int[]{-2, -2, -2, -2, -2};
        expectedGrid[0] = new int[]{-2, -2, -2, -2, -2};

        MazePoint goal = new MazePoint(5, 5);

        MockGridMovement mockGridMovement = new MockGridMovement(expectedGrid);
        GridWorldRunner runner = new GridWorldRunner(goal, mockGridMovement);
        runner.run();
    }

    @Test
    public void test3(){
        int[][] expectedGrid = new int[5][];
        expectedGrid[4] = new int[]{-2, -2, -1, -2, -2};
        expectedGrid[3] = new int[]{-2, -2, -1, -2, -2};
        expectedGrid[2] = new int[]{-2, -2, -2, -2, -2};
        expectedGrid[1] = new int[]{-2, -2, -2, -2, -2};
        expectedGrid[0] = new int[]{-2, -2, -2, -2, -2};

        MazePoint goal = new MazePoint(0, 2);

        MockGridMovement mockGridMovement = new MockGridMovement(expectedGrid);
        GridWorldRunner runner = new GridWorldRunner(goal, mockGridMovement);
        runner.run();
    }


}
