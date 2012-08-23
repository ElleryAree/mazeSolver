package movement;

import grid.PositionInGrid;
import head.Sense;
import maze.Direction;

/**
 * Date: 8/19/12
 */
public interface RunnerWithSenses {
    PositionInGrid move(Direction direction, PositionInGrid currentPosition);

    void getSense(PositionInGrid position);
}
