package test;

public class TestDLL {
    private native String getMsg();

    static {
        System.loadLibrary("TestDLL");
    }

    public static void main(String[] ars) {
        System.out.println(new TestDLL().getMsg());
    }
}
