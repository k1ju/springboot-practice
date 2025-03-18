package me.shinsunyoung.springbootdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Controller
public class ExampleController {

    //컨트롤러에서 return은 뷰를 반환한다!!
    //따라서 컨트롤러의 return은 resource/templates에서 example.html을 찾아 반환
    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model){ // 모델: 뷰로 데이터를 넘겨주는 객체
        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동","독서"));

        model.addAttribute("person", examplePerson); // Person 객체 저장
        model.addAttribute("today", LocalDate.now());

        return "example"; //example.html 뷰 반환
    }


    @Setter
    @Getter
    class Person{
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
