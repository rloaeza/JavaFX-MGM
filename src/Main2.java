public class Main2 {
    public static void main(String[] args) throws Exception {
        Class mgm = Class.forName("Main");
        Main m = (Main)mgm.newInstance();

        ClassLoader classLoader = mgm.getClassLoader();




    }
}
