package movement;

import junit.framework.Assert;
import localization.maze.Direction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrandRouteTest {
    @Test
    public void test(){
        Map<Pair, Integer> variations = new HashMap<Pair, Integer>();

        Direction[] possibleDirections = new Direction[]{Direction.FRONT, Direction.LEFT, Direction.RIGHT, Direction.BACK};

        for (Direction from : possibleDirections){
            for (Direction to : possibleDirections){
                variations.put(new Pair(from, to), 0);
            }
        }

        Assert.assertTrue("Master map contains all variations", variations.size() == 16);

        List<Direction> directions = new ArrayList<Direction>();
        MovementTest.generateGrandRoute(directions);

        String errors = "";

        for (int i=0; i<directions.size() - 1; i++){
            Pair pair = new Pair(directions.get(i), directions.get(i + 1));
            if (!variations.containsKey(pair)){
                errors += "Strange pair: " + pair;
                continue;
            }

            variations.put(pair, variations.get(pair) + 1);
        }

        for (Map.Entry<Pair, Integer> entry : variations.entrySet()){
            if (entry.getValue() != 1){
                errors += "Pair " + entry.getKey() + (entry.getValue() == 0 ?
                        " was not tested" :
                        (" was tested "  + entry.getValue() + " times")) + "\n" ;
            }
        }

        Assert.assertTrue(errors, errors.isEmpty());
    }

    private class Pair{
        Direction from;
        Direction To;

        private Pair(Direction from, Direction to) {
            this.from = from;
            To = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;

            Pair pair = (Pair) o;

            if (To != pair.To) return false;
            if (from != pair.from) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = from != null ? from.hashCode() : 0;
            result = 31 * result + (To != null ? To.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return from + " -> " + To;
        }
    }
}
