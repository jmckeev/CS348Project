import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Initializer initializer = new Initializer();
        //ArrayList<String> arrayList = initializer.getQuery().getTableColumn();
//        System.out.println("----------");
//        System.out.println(initializer.getQuery().sendQuery("SELECT * FROM Student;"));
//        System.out.println("----------");
//        ArrayList<String> list = initializer.getQuery().getColumns(initializer.getQuery().sendQuery("SELECT * FROM Student;"));
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
        //System.out.println(initializer.getQuery().getTable("SELECT * FROM Book;"));
        if (initializer.option.equals("Student")) {
            StudentDriver studentDriver = new StudentDriver(initializer);
        } else {

        }
        initializer.cleanup();
    }
}
