package com.example.QuizzApp.controllers;

import com.example.QuizzApp.dto.QuizStatisticDTO;
import com.example.QuizzApp.models.QuizStatistic;
import com.example.QuizzApp.repositories.QuizRepository;
import com.example.QuizzApp.repositories.QuizStatisticRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quiz/{hash}")
public class QuizController {
    private final static DateTimeFormatter CUSTOM_FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final QuizRepository quizRepository;
    private final ModelToDtoMapper modelToDtoMapper;
    private final QuizStatisticRepository quizStatisticRepository;

    public QuizController(QuizRepository quizRepository, ModelToDtoMapper modelToDtoMapper, QuizStatisticRepository quizStatisticRepository) {
        this.quizRepository = quizRepository;
        this.modelToDtoMapper = modelToDtoMapper;
        this.quizStatisticRepository = quizStatisticRepository;
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
        var mv = new ModelAndView("quizStat");
        Optional<Integer> id = quizRepository.findIdByHash(hash);
        List<QuizStatistic> statisticList = quizStatisticRepository.findAllByQuizId(id.get());
        List<QuizStatisticDTO> statisticDTO = modelToDtoMapper.listQuizStatisticToDTO(statisticList);
        mv.addObject("stats", statisticDTO);
        return mv;
    }
}
