package me.progfrog.mallang.gamification.controller;

import me.progfrog.mallang.gamification.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles(profiles = "test")
@WebMvcTest(AdminController.class)
public class AdminControllerEnabledTest {

    @MockBean
    private AdminService adminService;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("테스트 프로파일이 설정된 경우 컨트롤러가 예상한대로 동작하는지 확인")
    public void deleteDatabaseTest() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                        post("/gamification/admin/delete-db")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(adminService).deleteDatabaseContents();
    }
}
