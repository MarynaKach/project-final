package com.javarush.jira.bugtracking;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.common.error.AlreadySubscribedException;
import com.javarush.jira.common.error.NotFoundException;
import com.javarush.jira.login.internal.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubscriptionServiceTest extends AbstractControllerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;
    @Autowired
    private SubscriptionService subscriptionService;
}
