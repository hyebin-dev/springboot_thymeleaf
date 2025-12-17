package springBoot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.color.ICC_Profile;
import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class TestController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("msg", "Test Thymeleaf!!");
        List<String> list = Arrays.asList("aaa","bbb","ccc","ddd","eee","fff");
        model.addAttribute("list", list);
        return "index";
    }

    @GetMapping("/ex01")
    public String ex01(Model model){
        List<String> list = Arrays.asList("kim","lee","hong","choi","park","oh");
        model.addAttribute("list",list);
        return "/ex01/ex01";
    }

    @GetMapping("/ex02")
    public String ex02(@RequestParam("name") String name,
                       @RequestParam("age") int age,
                       Model model){
        log.info("name >> {}", name);
        log.info("age >> {}", age);
        TestVO testVO = new TestVO(name, age);
        model.addAttribute("testVO",testVO);
        return "/ex01/ex02";
    }

    @GetMapping("/ex03")
    public String ex03(Model model){
        List<String> strList = IntStream.range(1,10)
                .mapToObj(i -> "data"+i)
                .toList();
        log.info("strList >> {}", strList);
        model.addAttribute("strList", strList);

        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 500);
        map.put("orange", 300);
        map.put("banana", 600);
        map.put("kiwi", 700);
        model.addAttribute("map", map);

        List<TestVO> testList = new ArrayList<>();
        testList.add(new TestVO("hong",30));
        testList.add(new TestVO("kim",20));
        testList.add(new TestVO("park",25));
        testList.add(new TestVO("choi",35));
        testList.add(new TestVO("lee",30));
        model.addAttribute("testList", testList);

        return "/ex01/ex03";
    }
}
