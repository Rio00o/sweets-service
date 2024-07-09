package com.example.sweets.integrationtest;
import com.example.sweets.Sweet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SweetApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/sweets.yml")
    @Transactional
    void SQLテーブルに登録されているスイーツが全件取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sweets"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json("""
                [
                    {
                        "id": 1,
                        "name": "博多通りもん",
                        "company": "明月堂",
                        "price": 720,
                        "prefecture": "福岡県"
                    },
                    {
                        "id": 2,
                        "name": "萩の月",
                        "company": "菓匠三全",
                        "price": 1500,
                        "prefecture": "宮城県"
                    },
                    {
                        "id": 3,
                        "name": "白い恋人",
                        "company": "石屋製菓",
                        "price": 1036,
                        "prefecture": "北海道"
                    },
                    {
                        "id": 4,
                        "name": "東京ばな奈",
                        "company": "東京ばな奈ワールド",
                        "price": 1198,
                        "prefecture": "東京都"
                    }
                ]
                """));
    }

    @Test
    @DataSet(value = "datasets/sweets.yml")
    @Transactional
    void IDが1で登録されているスイーツを指定したとき正常にIdが1のスイーツが返されること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sweets/1"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json("""
                {
                    "id": 1,
                    "name": "博多通りもん",
                    "company": "明月堂",
                    "price": 720,
                    "prefecture": "福岡県"
                },
                """));
    }

    @Test
    @DataSet(value = "datasets/sweets.yml")
    @Transactional
    void IDが999のスイーツを指定したとき例外処理が発生すること () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sweets/999"))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
