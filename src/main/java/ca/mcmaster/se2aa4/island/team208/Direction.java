package ca.mcmaster.se2aa4.island.team208;

public enum Direction {
    //maintain order N->E->S->W, next ordinal is to the right of previous
    N,E,S,W;
    public static Direction getLeft(Direction d){
        return Direction.values()[(d.ordinal()+3)%4];
    }
    public static Direction getOpposite(Direction d){
        return Direction.values()[(d.ordinal()+2)%4];
    }
    public static Direction getRight(Direction d){
        return Direction.values()[(d.ordinal()+1)%4];
    }
    public static Direction parseDirection(String direction){
        direction=direction.toUpperCase();
        return switch (direction) {
            case "N", "NORTH" -> Direction.N;
            case "S", "SOUTH" -> Direction.S;
            case "E", "EAST" -> Direction.E;
            case "W", "WEST" -> Direction.W;
            default ->
                    throw new IllegalArgumentException("Cannot parse Direction (must be the full direction name or one letter representation).");
        };
    }
}
