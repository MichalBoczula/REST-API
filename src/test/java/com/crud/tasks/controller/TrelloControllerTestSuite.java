package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TrelloController.class)
public class TrelloControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception {
        //given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        when(trelloFacade.fetchTrelloBoard()).thenReturn(trelloBoardDtos);
        //when & then
        mockMvc.perform(get("/v1/trello/boards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception{
        //given
        final List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "test list", false));
        final List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "test board", trelloListDtos));
        when(trelloFacade.fetchTrelloBoard()).thenReturn(trelloBoardDtos);
        //when & then
        mockMvc.perform(get("/v1/trello/boards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Trello Board fields
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("test board")))
                //Trello List fields
                .andExpect(jsonPath("$[0].lists", hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", is("1")))
                .andExpect(jsonPath("$[0].lists[0].name", is("test list")))
                .andExpect(jsonPath("$[0].lists[0].closed", is(false)));
    }

    @Test
    public void shouldCreateTrelloCard() throws Exception {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "test",
                "test desc",
                "top",
                "1");
        TrelloCardDto cardDto = new TrelloCardDto(
                "test",
                "test desc",
                "top",
                "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "test",
                "http://test.com");
        when(trelloFacade.createCard(cardDto)).thenReturn(createdTrelloCardDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);
        //when & then
        mockMvc.perform(post("/v1/trello/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(jsonContent));
//                .andExpect(jsonPath("$[0].id", is("1")))
//                .andExpect(jsonPath("$[0].name", is("test")))
//                .andExpect(jsonPath("$[0].shortUrl", is("http://test.com")));
    }
}