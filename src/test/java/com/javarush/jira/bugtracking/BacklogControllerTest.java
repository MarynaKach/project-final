package com.javarush.jira.bugtracking;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.to.TaskTo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.List;

import static com.javarush.jira.bugtracking.BugTrackingTestData.getTaskList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BacklogControllerTest extends AbstractControllerTest {

        @MockBean
        private BacklogService backlogService;
        @Test
        public void testGetTasks() throws Exception {
            List<TaskTo> tasks = getTaskList();
            Mockito.when(backlogService.getBacklogTasks()).thenReturn(tasks);
            mockMvc.perform(get("/tasks/backlog")
                            .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com").password("admin").roles("ADMIN")))
                    .andExpect(status().isOk())
                    .andExpect(view().name("backlog"))
                    .andExpect(model().attribute("tasks", tasks))
                    .andExpect(model().attribute("current_page", 1))
                    .andExpect(model().attributeDoesNotExist("page_numbers"));
            Mockito.verify(backlogService, Mockito.times(1)).getBacklogTasks();
        }
}
