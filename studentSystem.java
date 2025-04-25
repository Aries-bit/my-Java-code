import java.util.ArrayList;
import java.util.Scanner;

public class studentSystem {
    public static void startStudentSystem() {
        ArrayList<student> students=new ArrayList<>();

        loop:while (true) {
            System.out.println("--------------欢迎来到学生管理系统----------------");
            System.out.println("1.添加学生");
            System.out.println("2.删除学生");
            System.out.println("3.修改学生");
            System.out.println("4.查询学生");
            System.out.println("5.退出");
            System.out.println("请输入你的选择：");

            Scanner sc=new Scanner(System.in);
            String choice=sc.next();
            switch (choice) {
                case "1" -> addStudent(students);
                case "2" -> deleteStudent(students);
                case "3" -> updateStudent(students);
                case "4" -> queryStudent(students);
                case "5" -> {
                    System.out.println("已退出");
                    break loop;
                }
                default -> System.out.println("输入有误");
            }
        }
    }

    //添加学生
    public static void addStudent(ArrayList<student> students) {
        System.out.println("添加学生");

        Scanner sc=new Scanner(System.in);
        System.out.println("请输入学生的id");
        String id=sc.next();
        System.out.println("请输入学生的姓名");
        String name = sc.next();
        System.out.println("请输入学生的年龄");
        int age=sc.nextInt();
        System.out.println("请输入学生的家庭住址");
        String home=sc.next();

        student s=new student(id,name,age,home);
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                System.out.println("id重复");
                return;
            }

        }
        students.add(s);
        System.out.println("添加成功");


    }

    //删除学生
    public static void deleteStudent(ArrayList<student> students){
        System.out.println("删除学生");

        Scanner sc=new Scanner(System.in);
        System.out.println("请输入要删除的学生id");
        String id=sc.next();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.remove(i);
                System.out.println("删除成功");
                return;
            }
        }
        System.out.println("id不存在");
    }

    //修改学生
    public static void updateStudent(ArrayList<student> students){
        System.out.println("修改学生");

        Scanner sc=new Scanner(System.in);
        System.out.println("请输入要修改的学生id");
        String id=sc.next();

        int index=0;
        for (int i = 0; i < students.size(); i++) {
            if (!students.get(i).getId().equals(id)) {
                System.out.println("id不存在");
                return;
            }else {
                index=i;
            }
        }
        System.out.println("重新输入学生的姓名");
        String name = sc.next();
        System.out.println("重新输入学生的年龄");
        int age=sc.nextInt();
        System.out.println("重新输入学生的家庭住址");
        String home=sc.next();

        students.get(index).setAge(age);
        students.get(index).setHome(home);
        students.get(index).setName(name);
        System.out.println("修改成功");
    }

    //查询学生
    public static void queryStudent(ArrayList<student> students){
        System.out.println("查询学生");
        if (students.size()==0) {
            System.out.println("当前无学生信息，请添加后重试");
        }else {
            System.out.println("id        姓名    年龄    家庭住址");
            for (int i = 0; i < students.size(); i++) {
                System.out.println(students.get(i).getId()+"  "+students.get(i).getName()+"    "
                        +students.get(i).getAge()+"      "+students.get(i).getHome());
            }
        }

    }
}
