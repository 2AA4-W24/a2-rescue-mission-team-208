package ca.mcmaster.se2aa4.island.team208;

public enum Direction {
    //maintain order N->E->S->W, next ordinal is to the right of previous
    N,E,S,W;
    public static Direction getLeft(Direction d){
        int index = (d.ordinal()-1)%4;
        if(index<0)index+=4;
        return Direction.values()[index];
    }
    public static Direction getOpposite(Direction d){
        return Direction.values()[(d.ordinal()+2)%4];
    }
    public static Direction getRight(Direction d){
        return Direction.values()[(d.ordinal()+1)%4];
    }
    public static Direction parseDirection(String d){
        d=d.toUpperCase();
        return switch (d) {
            case "N", "NORTH" -> Direction.N;
            case "S", "SOUTH" -> Direction.S;
            case "E", "EAST" -> Direction.E;
            case "W", "WEST" -> Direction.W;
            default ->
                    throw new IllegalArgumentException("Cannot parse Direction (must be the full direction name or one letter representation).");
        };
    }
    public static int[] getDirectionVector(Direction d){
        switch(d){
            case N -> {
                return new int[]{0,1};
            }
            case S -> {
                return new int[]{0,-1};
            }
            case E -> {
                return new int[]{1,0};
            }
            case W -> {
                return new int[]{-1,0};
            }
            default -> {
                throw new NullPointerException("Invalid argument.");
            }
        }
    }
}
