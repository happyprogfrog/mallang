package me.progfrog.mallang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.progfrog.mallang.domain.Multiplication;
import me.progfrog.mallang.domain.MultiplicationResultAttempt;
import me.progfrog.mallang.domain.User;
import me.progfrog.mallang.service.MultiplicationService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
class MultiplicationResultAttemptControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mvc;

    // 이 객체는 initFields() 메서드를 이용해 자동으로 초기화
    private JacksonTester<MultiplicationResultAttempt> jsonResultAttempt;
    private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttemptList;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    @DisplayName("사용자가 보낸 답안이 맞음")
    void postResultReturnCorrect() throws Exception {
        genericParameterizedTest(true);
    }

    @Test
    @DisplayName("사용자가 보낸 답안이 틀림")
    void postResultReturnNotCorrect() throws Exception {
        genericParameterizedTest(false);
    }

    void genericParameterizedTest(final boolean correct) throws Exception {
        // given (지금 서비스를 테스트하는 것이 아님!)
        given(multiplicationService
                .checkAttempt(any(MultiplicationResultAttempt.class)))
                .willReturn(correct);

        User user = new User("Frog");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, correct);

        // when
        MockHttpServletResponse response = mvc.perform(post("/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResultAttempt.write(attempt).getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResultAttempt.write(
                new MultiplicationResultAttempt(
                        attempt.getUser(),
                        attempt.getMultiplication(),
                        attempt.getResultAttempt(),
                        correct)
                ).getJson());
    }

    @Test
    @DisplayName("사용자의 통계 정보를 조회")
    public void getUserStats() throws Exception {
        // given
        User user = new User("Frog");
        Multiplication multiplication = new Multiplication(50, 70);

        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, true);
        List<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(attempt, attempt);
        given(multiplicationService.getStatsForUser("Frog")).willReturn(recentAttempts);

        // when
        MockHttpServletResponse response = mvc.perform(get("/results")
                .param("alias", "Frog"))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResultAttemptList.write(recentAttempts).getJson());
    }

    @Test
    @DisplayName("Id로 답안 조회하기")
    void getResultById() throws Exception {
        // given
        User user = new User("Frog");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, true);
        given(multiplicationService.getResultById(4L)).willReturn(attempt);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/results/4"))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResultAttempt.write(attempt).getJson());
    }
}