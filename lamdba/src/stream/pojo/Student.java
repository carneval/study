package stream.pojo;

public class Student {

    private String name;
    private Integer age;
    private Gender gender;
    private Grade grade;

    public Student(String name, Integer age, Gender gender, Grade grade) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
    }

    public Student() {

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
          "name='" + name + '\'' +
          ", age=" + age +
          ", gender=" + gender +
          ", grade=" + grade +
          '}';
    }
}
