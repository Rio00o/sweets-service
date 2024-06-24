package com.example.sweets;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.IntPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class SweetMapperTest {

    @Autowired
    private SweetMapper sweetMapper;

    @Test
    @DataSet(value = "datasets/sweets.yml")
    @Transactional
    void すべてのスイーツが取得できること() {
        List<Sweet> sweets = sweetMapper.findAll();
        assertThat(sweets)
                .hasSize(4)
                .contains(
                        new Sweet(1, "博多通りもん", "明月堂", 720, "福岡県"),
                        new Sweet(2, "萩の月", "菓匠三全", 1500, "宮城県"),
                        new Sweet(3, "白い恋人", "石屋製菓", 1036, "北海道"),
                        new Sweet(4, "東京ばな奈", "東京ばな奈ワールド", 1198, "東京都")
                );
    }

    @Test
    @DataSet(value = "datasets/sweets.yml")
    @Transactional
    void 指定したIDのスイーツが取得できること () {
        Optional<Sweet> actual = sweetMapper.findById(1);
        Sweet sweet1 = new Sweet(1, "博多通りもん", "明月堂", 720, "福岡県");
        assertThat(actual.get()).isEqualTo(new Sweet(1, "博多通りもん", "明月堂", 720, "福岡県"));
    }

    @Test
    @DataSet(value = "datasets/sweets.yml")
    @Transactional
    void 存在しないIDを指定した場合は空のOptionalが返ること () {
        Optional<Sweet> sweets = sweetMapper.findById(999);
        assertThat(sweets).isEmpty();
    }
}
