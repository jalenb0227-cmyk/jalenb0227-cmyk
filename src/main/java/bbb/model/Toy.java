package bbb.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Toy {
    STICK(Symbol.STICK,5),
    BONE(Symbol.BONE,4),
    CHEW_ROPE(Symbol.CHEW_ROPE,3),
    SQUEAKER(Symbol.SQUEAKER,3),
    SHOE(Symbol.SHOE,2);

    private final Symbol sym;
    private final int len;

    Toy(Symbol sym, int len){
        this.sym = sym;
        this.len = len;
    }
    
    public Symbol getSymbol(){
        return sym;
    }

    public int getLen(){
        return len;
    }

    public static Set<Character> getAllAsciiCharaters(){
        return Arrays.stream(Toy.values())
                .map(toy -> toy.getSymbol()
                .getChar())
                .collect(Collectors.toSet());
    }
}
