package com.authentication.controller;


import com.authentication.dto.CreateAccountRequest;
import com.authentication.service.OnlineAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OnlineAccountController.class)
public class OnlineAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OnlineAccountService onlineAccountService;

    @Test
    @SneakyThrows
    public void testCreateOnlineAccount() {
        doNothing().when(onlineAccountService).createAccount("77853449", "testUser", "testPassword");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(new CreateAccountRequest("77853449",
                "testUser", "testPassword"));

        mockMvc.perform(post("/online-accounts").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(onlineAccountService, times(1))
                .createAccount("77853449", "testUser", "testPassword");
    }
}
