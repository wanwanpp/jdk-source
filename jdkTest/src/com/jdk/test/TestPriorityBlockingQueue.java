package com.jdk.test;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author 王萍
 * @date 2017/11/24 0024
 */
//可以看出PriorityBlockingQueue维护的是一个最小堆。
public class TestPriorityBlockingQueue {
    public static void main(String[] args) {

        PriorityBlockingQueue<Student> students = new PriorityBlockingQueue<>();
        students.offer(new Student("aaa",12));
        students.offer(new Student("bbb",13));
        students.offer(new Student("ccc",9));
        System.out.println(students.poll().getName());
        System.out.println(students.poll().getName());
        System.out.println(students.poll().getName());
    }
}

class Student implements Comparable<Student> {
    private String name;
    private Integer age;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public int compareTo(Student student) {
        return this.age > student.getAge() ? 1 : -1;
    }
}
