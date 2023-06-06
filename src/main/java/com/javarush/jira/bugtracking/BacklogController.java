package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.to.TaskTo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/tasks/backlog")
public class BacklogController {
    @Autowired
    private final BacklogService backlogService;

    @GetMapping()
    public String getTasks(Model model,
                           HttpServletRequest request,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "limit", required = false, defaultValue = "4") int limit) {
        List<TaskTo> taskMap = backlogService.getBacklogTasks();
        int offset = (page - 1) * limit;
        int totalCount = taskMap.size();
        int totalPages = (int) Math.ceil((double) totalCount / limit);
        List<TaskTo> pagedTasks = taskMap.stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
        model.addAttribute("tasks", pagedTasks);
        model.addAttribute("current_page", page);
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .filter(pageNumber -> pageNumber <= totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "backlog";
    }
}
