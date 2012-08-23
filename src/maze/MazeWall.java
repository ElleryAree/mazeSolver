package maze;

/**
 * Wall of the maze.
 *
 * User: elleryaree
 * Date: 8/13/12
 */
public class MazeWall {
    private MazePoint start;
    private MazePoint end;

    public MazeWall(MazePoint start, MazePoint end) {
        this.start = start;
        this.end = end;
    }

    public MazePoint getStart() {
        return start;
    }

    public void setStart(MazePoint start) {
        this.start = start;
    }

    public MazePoint getEnd() {
        return end;
    }

    public void setEnd(MazePoint end) {
        this.end = end;
    }
}
