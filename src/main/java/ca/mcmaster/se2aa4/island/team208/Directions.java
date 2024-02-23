package ca.mcmaster.se2aa4.island.team208;

public class Directions {
    //methods rely on order of Direction being N->E->S->W
    public static Direction getLeft(Direction d){
        return Direction.values()[(d.ordinal()-1)%4];
    }
    public static Direction getOpposite(Direction d){
        return Direction.values()[(d.ordinal()+2)%4];
    }
    public static Direction getRight(Direction d){
        return Direction.values()[(d.ordinal()+1)%4];
    }
}
