package com.example.demo.controller;

import com.example.demo.domain.BoardVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.PagingHandler;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board/*")
@Slf4j
@Controller
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(BoardVO boardVO){
        log.info(">>> boardVO >> {}", boardVO);
        boardService.insert(boardVO);
        return "index";
    }

    @GetMapping("/list")
    public void list(Model model, PagingVO pagingVO){
        List<BoardVO> list = boardService.getList(pagingVO);
        int totalCount = boardService.getTotalCount(pagingVO); // 검색어를 포함한 (전체 페이지 개수 가져오기)
        PagingHandler ph = new PagingHandler(totalCount,pagingVO);
        model.addAttribute("list", list);
        model.addAttribute("ph", ph);
    }

    @GetMapping("/detail")
    public void detail(Model model, @RequestParam("bno") long bno){
        BoardVO boardVO = boardService.getDetail(bno);
        model.addAttribute("board", boardVO);
    }

    @PostMapping("/modify")
    public String update(BoardVO boardVO, RedirectAttributes redirectAttributes){
        boardService.update(boardVO);
        redirectAttributes.addAttribute("bno",boardVO.getBno());
        return "redirect:/board/detail";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("bno")long bno){
        boardService.delete(bno);
        return "redirect:/board/list";
    }




}
