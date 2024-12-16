package readerFile;

public class PeopleJackson {
    private String name;
    private String age;
    private String sex;

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public PeopleJackson(String name, String age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public PeopleJackson() {

    }
}
