package movement;

import localization.grid.PositionInGrid;
import localization.maze.Direction;

/**
 * Date: 8/19/12
 */
public interface RunnerWithSenses {
    PositionInGrid move(Direction direction, PositionInGrid currentPosition);

    void getSense(PositionInGrid position);
}
