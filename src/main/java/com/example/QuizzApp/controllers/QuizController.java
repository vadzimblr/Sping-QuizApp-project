package com.example.QuizzApp.controllers;

import com.example.QuizzApp.repositories.QuizRepository;
import com.example.QuizzApp.utils.ModelToDtoMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/quiz/{hash}")
public class QuizController {
    private final static DateTimeFormatter CUSTOM_FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final QuizRepository quizRepository;
    private final ModelToDtoMapper modelToDtoMapper;

    public QuizController(QuizRepository quizRepository, ModelToDtoMapper modelToDtoMapper) {
        this.quizRepository = quizRepository;
        this.modelToDtoMapper = modelToDtoMapper;
    }

    @GetMapping("/details")
    public ModelAndView showStartQuizPage(@PathVariable String hash, HttpServletRequest httpServletRequest){
        ModelAndView mv = new ModelAndView("startquiz");
        var quiz = quizRepository.findByHash(hash);
        mv.addObject("quiz", quiz.get());
        mv.addObject("currUrl", httpServletRequest.getRequestURI());
        return mv;
    }
    @GetMapping("/details/start-quiz")
    public RedirectView startQuiz(@PathVariable String hash,HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        LocalDateTime currentDateTime = LocalDateTime.now();
        session.setAttribute("startTime_"+hash,currentDateTime.format(CUSTOM_FORMATER));
        return new RedirectView("/quiz/{hash}/");
    }
    @GetMapping("/")
    public ModelAndView showQuizPage(@PathVariable String hash, HttpServletRequest httpServletRequest ){
        String sessionQuizStartTime = (String) httpServletRequest.getSession().getAttribute("startTime_"+hash);
        if (sessionQuizStartTime == null) {
            return new ModelAndView("redirect:/quiz/{hash}/details");
        }
        ModelAndView mv = new ModelAndView("quiz");
        var quiz = quizRepository.findByHash(hash);
        if(quiz.isPresent()){
            var quizDTO = modelToDtoMapper.quizToQuizDTO(quiz.get());
            mv.addObject("quiz", quizDTO);
        }
        else{
            return new ModelAndView("redirect:/"); //tmp
        }
        return mv;
    }
    @GetMapping("/stat")
    public ModelAndView showUserStatistic(@PathVariable String hash){
        return new ModelAndView("quizStat");
    }
}
