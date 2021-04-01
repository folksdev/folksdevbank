import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        System.out.println();
/*

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(2, "C");
        String b = map.get(2);
        System.out.println(b);
        System.out.println(map);*/


        Map<Integer, String> mapJava8 = Map.of(
                1,"A",
                2, "B",
                3, "C");

        /*mapJava8.forEach((key, value) ->
                System.out.print(key + " : " + value + ", "));

        System.out.println();
        System.out.println("keys: " + mapJava8.keySet());
        System.out.println("values: " + mapJava8.values());*/
        List<Student> studentList = List.of(
                new Student(1,"John"),
                new Student(2, "Dee"),
                new Student(3, "Alice")
        );

        //String list to Count Map
        List<String> inputList = List.of("A","B","C","A","C","D");
        Map<String, Long> counters = inputList.stream()
                .collect(Collectors.groupingBy(p -> p,
                        Collectors.counting()));
        System.out.println(counters);
        //Object list to id:Object Map
        Map<Integer, Student> map = studentList.stream()
                .collect(Collectors.toMap(
                        Student::getId, student -> student));
        System.out.println(map);



    }

    private static class Student{
        private Integer id;
        private String name;

        public Student(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}

