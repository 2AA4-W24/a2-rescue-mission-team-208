package ca.mcmaster.se2aa4.island.team208.Enums;

import java.util.Map;
import java.util.HashMap;

public enum Direction {
    //maintain order N->E->S->W, next ordinal is to the right of previous
    N,E,S,W;
    private final static Map<String, Direction> mapDirParse = new HashMap<>();
    private final static Map<Direction, int[]> mapDirVec = new HashMap<>();
    static {
        mapDirParse.put("N", Direction.N);
        mapDirParse.put("S", Direction.S);
        mapDirParse.put("E", Direction.E);
        mapDirParse.put("W", Direction.W);

        mapDirVec.put(Direction.N, new int[]{0,1});
        mapDirVec.put(Direction.S, new int[]{0,-1});
        mapDirVec.put(Direction.E, new int[]{1,0});
        mapDirVec.put(Direction.W, new int[]{-1,0});
    }
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
    public static Direction parseDirection(String d) {
        return mapDirParse.get(d);
    }
    public static int[] getDirectionVector(Direction d){
        return mapDirVec.get(d);
    }
}
