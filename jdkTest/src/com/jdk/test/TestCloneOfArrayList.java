package com.jdk.test;

/**
 * @author Õı∆º
 * @date 2018/2/19 0019
 */
public class TestCloneOfArrayList {

    public static void main(String[] args) {
        //≤‚ ‘«≥∏¥÷∆
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        ArrayList list2 = (ArrayList)list.clone();
//        list2.add(5);
//        System.out.println(list);
//        System.out.println(list2);

        //≤‚ ‘…Ó∏¥÷∆
//        ArrayList<User> list = new ArrayList<>();
//        list.add(new User("wanwanpp", 19));
//        ArrayList<User> list2 = (ArrayList<User>) list.clone();
//        list2.get(0).age=119;
//        System.out.println(list);
//        System.out.println(list2);


    }

    static class User {

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String name;
        public int age;

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
