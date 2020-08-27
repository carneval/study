package stream;

import stream.pojo.Gender;
import stream.pojo.Grade;
import stream.pojo.Student;

import java.util.*;
import java.util.stream.Collectors;

public class CollectDemo {

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
          new Student("小明", 10, Gender.MALE, Grade.ONE),
          new Student("大明", 9, Gender.MALE, Grade.THREE),
          new Student("小白", 8, Gender.FEMALE, Grade.TWO),
          new Student("小黑", 13, Gender.FEMALE, Grade.FOUR),
          new Student("小红", 7, Gender.FEMALE, Grade.ONE),
          new Student("小黄", 13, Gender.MALE, Grade.THREE),
          new Student("小青", 13, Gender.FEMALE, Grade.TWO),
          new Student("小紫", 9, Gender.FEMALE, Grade.ONE),
          new Student("小王", 6, Gender.MALE, Grade.FOUR),
          new Student("小李", 6, Gender.MALE, Grade.ONE),
          new Student("小马", 13, Gender.FEMALE, Grade.FOUR),
          new Student("小刘", 14, Gender.MALE, Grade.ONE),
          new Student("小张", 10, Gender.MALE, Grade.FOUR)
        );

        //得到所有学生的年龄列表
        //多使用方法引用
        //s -> s.getAge() --> Student::getAge, 不会多生成一个类似lambda$0这样的函数
        List<Integer> ages = students.stream()
          .map(Student::getAge)
          .collect(Collectors.toList());
        System.out.println("所有学生的年龄：" + ages);

        //去重
        Set<Integer> ageSet = students.stream()
          .map(Student::getAge)
          .collect(Collectors.toSet());
        //指定集合类型
        Set<Integer> ageSet2 = students.stream()
          .map(Student::getAge)
          .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("去重 -> 所有学生的年龄：" + ageSet);

        //统计汇总信息
        IntSummaryStatistics ageSummary = students.stream()
          .collect(Collectors.summarizingInt(Student::getAge));
        System.out.println("年龄汇总信息：" + ageSummary);

        //分块
        Map<Boolean, List<Student>> genders = students.stream()
          .collect(Collectors.partitioningBy(s -> s.getGender() == Gender.MALE));
        System.out.println("男女学生列表：" + genders);

        //分组
        Map<Grade, List<Student>> grades = students.stream()
          .collect(Collectors.groupingBy(Student::getGrade));
        System.out.println("学生班级列表：" + grades);

        //得到所有班级学生的个数
        Map<Grade, Long> gradesCount = students.stream()
          .collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
        System.out.println("学生班级个数列表：" + gradesCount);
    }
}
