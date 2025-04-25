import javax.print.attribute.standard.JobOriginatingUserName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class app {
    public static void main(String[] args) {
        ArrayList<user> users = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("欢迎来到学生管理系统");
            System.out.println("请选择操作1登录 2注册 3忘记密码 4退出系统");
            int chose = sc.nextInt();
            switch (chose) {
                case 1 -> login(users);
                case 2 -> register(users);
                case 3 -> forgetPassword(users);
                case 4 -> {
                    System.out.println("谢谢使用，再见");
                    System.exit(0);
                }
                default -> System.out.println("没有这个选项");
            }
        }
    }

    //登录
    public static void login(ArrayList<user> users) {
        Scanner sc = new Scanner(System.in);
        System.out.println("登录");
        String username = "admin";
        String password = "password";
        String code = "";
        int ans = 0;
        while (true) {
            if (ans == 3) {
                System.out.println("登录失败，三次机会已用完");
                break;
            }
            //用户名
            System.out.println("请输入用户名");
            username = sc.nextLine();
            boolean flag1 = contains(users, username);
            if (flag1) {
                System.out.println("用户名未注册，请先注册");
                break;
            }
            //验证码
            String code1 =createCode();
            System.out.println("验证码为"+code1);
            code = sc.nextLine();
            if (!code.equalsIgnoreCase(code1)) {
                System.out.println("验证码错误，请重新登录");
                break;
            }
            //密码
            System.out.println("请输入密码");
            password = sc.nextLine();
            boolean flag2 = checkPassword(users, username, password);
            if (flag2) {
                System.out.println("密码正确,登录成功");
                //登录成功后，进入学生管理系统
                studentSystem ss = new studentSystem();
                ss.startStudentSystem();
                break;
            } else {
                System.out.println("密码错误,请重新登录");
                ans++;
                break;
            }
        }

    }

    //注册
    public static void register(ArrayList<user> users) {
        System.out.println("注册");
        user User = new user();
        Scanner sc = new Scanner(System.in);
        String username;
        String password1;
        String password2;
        String userId;
        String phoneNumber;


        //开发过程中，先验证格式，再验证唯一性

        //用户名操作
         /* 用户名唯一
         用户名长度必须在3~15位之间
         只能是字母加数字的组合，但是不能是纯数字*/
        while (true) {
            System.out.println("输入你的用户名");
            username = sc.nextLine();

            //判断格式
            boolean flag1 = checkUsername(username);
            if (!flag1) {
                System.out.println("用户名格式错误,请重新输入");
                continue;
            }

            //判断用户名唯一性
            boolean flag2 = contains(users, username);
            if (!flag2) {
                System.out.println("用户名" + username + "已存在，请重新输入");
            } else {
                break;
            }

        }
        //密码操作
        while (true) {
            System.out.println("输入你的密码");
            password1 = sc.nextLine();
            System.out.println("再次输入你的密码");
            password2 = sc.nextLine();
            if (!password1.equals(password2)) {
                System.out.println("两次密码不一致，请重新输入");
                continue;
            } else {
                break;
            }
        }

        //身份证号操作

        /*长度为18位
          不能以0为开头
          前17位，必须都是数字
          最为一位可以是数字，也可以是大写X或小写x*/

        while (true) {
            System.out.println("请输入你的身份证号");
            userId = sc.nextLine();
            if (userId.length() != 18) {
                System.out.println("身份证号长度错误,请重新输入");
                continue;
            }
            boolean flag3 = checkUserId(userId);
            if (!flag3) {
                System.out.println("身份证号格式错误,请重新输入");
                continue;
            } else {
                break;
            }
        }

        //手机号操作

       /* 长度为11位
          不能以0为开头
          必须都是数字*/

        while (true) {
            System.out.println("请输入你的手机号");
            phoneNumber = sc.nextLine();
            boolean flag4 = checkPhoneNumber(phoneNumber);
            if (!flag4) {
                System.out.println("手机号格式错误,请重新输入");
                continue;
            } else {
                break;
            }
        }

        User.setUserName(username);
        User.setPassword(password1);
        User.setUserId(userId);
        User.setPhoneNumber(phoneNumber);

        users.add(User);
        System.out.println("注册成功");

        //遍历打印
        print(users);


    }

    //忘记密码
    public static void forgetPassword(ArrayList<user> users) {
        Scanner sc = new Scanner(System.in);
        out:while (true) {
            System.out.println("请输入用户名");
            String username = sc.nextLine();
            boolean flag1 = contains(users, username);
            if (flag1) {
                System.out.println("用户名未注册，请先注册");
                break;
            }
            System.out.println("请输入身份证号");
            String userId = sc.nextLine();
            System.out.println("请输入手机号");
            String phoneNumber = sc.nextLine();
            boolean flag2 = check(users, username, userId, phoneNumber);
            if (flag2) {
                System.out.println("验证成功，请输入新密码");
                String password = sc.nextLine();
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUserId().equals(userId)) {
                        users.get(i).setPassword(password);
                        System.out.println("密码修改成功");
                        break out;
                    }
                }

            }else {
                System.out.println("验证失败，请重新输入");
                continue;
            }
        }

    }

    //判断用户名格式
    public static boolean checkUsername(String username) {
        boolean flag = true;
        if (username.length() < 3 || username.length() > 15) {
            System.out.println("用户名长度必须在3~15位之间");
            flag = false;
        }
        //判断是否是字母加数字
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if ((c < '0' || c > '9') && (c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) {
                flag = false;
            }
        }
        //判断是否是纯数字
        int ans = 0;
        for (int i = 0; i < username.length(); i++) {
            char cc = username.charAt(i);
            if (cc >= '0' && cc <= '9') {
                ans++;
            }
        }
        if (ans == username.length()) {
            flag = false;
        }

        return flag;
    }

    //判断用户名唯一性
    public static boolean contains(ArrayList<user> users, String username) {
        boolean flag = true;
        for (int i = 0; i < users.size(); i++) {
            user User = users.get(i);
            if (User.getUserName().equals(username)) {
                flag = false;
            }
        }
        return flag;
    }

    //判断身份证号格式
    public static boolean checkUserId(String userId) {
        boolean flag = true;
        if (userId.length() != 18 || userId.charAt(0) == '0') {
            flag = false;
        }
        for (int i = 0; i < userId.length() - 1; i++) {
            char c = userId.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                flag = false;
            }
        }
        if (!((userId.charAt(userId.length() - 1) >= '0' && userId.charAt(userId.length() - 1) <= '9') || userId.charAt(userId.length() - 1) == 'x'
                || userId.charAt(userId.length() - 1) == 'X')) {
            flag = false;
        }
        return flag;

    }

    //判断手机号格式
    public static boolean checkPhoneNumber(String phoneNumber) {
        boolean flag = true;
        if (phoneNumber.length() != 11 || phoneNumber.charAt(0) == '0') {
            flag = false;
        }

        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                flag = false;
            }
        }
        return flag;

    }

    //遍历打印
    public static void print(ArrayList<user> users) {
        for (int i = 0; i < users.size(); i++) {
            user User = users.get(i);
            System.out.println("用户名" + User.getUserName() + " 密码" + User.getPassword() + " 身份证号" + User.getUserId() +
                    " 手机号" + User.getPhoneNumber());
        }
    }

    //验证密码是否正确
    public static boolean checkPassword(ArrayList<user> users, String username, String password) {
        boolean flag = false;
        for (int i = 0; i < users.size(); i++) {
            user User = users.get(i);
            if (User.getUserName().equals(username) && User.getPassword().equals(password)) {
                flag = true;
            }
        }
        return flag;
    }

    //生成验证码
    public static String createCode() {
       /* 长度为5
          由4位大写或者小写字母和1位数字组成，同一个字母可重复
          数字可以出现在任意位置
          比如：
          aQa1K*/

        String code = "";
        //创建一个集合存储所有大写小写字母
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }

        StringBuilder sb = new StringBuilder();
        //随机抽取四个字符
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);
        }

        int num = r.nextInt(10);
        sb.append(num);

        //打乱顺序
        char[] ch = sb.toString().toCharArray();
        int index = r.nextInt(ch.length);
        char temp = ch[ch.length-1];
        ch[ch.length-1] = ch[index];
        ch[index] = temp;

        //把字符数组转成字符串
        String code2 = new String(ch);

        return code2;
    }

    //判断手机号和身份证是否对应
    public static boolean check(ArrayList<user> users, String username, String userId, String phoneNumber) {
        boolean flag = true;
        for (int i = 0; i < users.size(); i++) {
            user User = users.get(i);
            if(User.getUserName().equals(username) && User.getUserId().equals(userId) && User.getPhoneNumber().equals(phoneNumber)) {
                flag = true;
            }
        }
        return flag;
    }

}
