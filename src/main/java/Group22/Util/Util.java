package Group22.Util;

public class Util {
    public static int cut_id(String s){
        String[] s2 = s.split("/");
        return Integer.parseInt(s2[5]);
    }
}
