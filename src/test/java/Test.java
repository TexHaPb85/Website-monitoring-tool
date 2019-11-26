import com.providesupportLLC.utils.NumericUtils;

public class Test {
    @org.junit.Test
    public void test(){
        String s = "ok ";
        s+=" crit, ";
        s+=" warn, ";
        s+=" some, ";
        System.out.println(s);
    }

    @org.junit.Test
    public void test2(){
        String s = "444 ";
        long num = -1;
        num=NumericUtils.parseIfPossible(s,num);
        System.out.println(num);
    }

}
