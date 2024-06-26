package com.example.sweets;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Sweet sweets = new Sweet(1, "博多通りもん", "明月堂", 720, "福岡県");
        assertThat(actual.get()).isEqualTo(new Sweet(1, "博多通りもん", "明月堂", 720, "福岡県"));
    }

    @Test
    @DataSet(value = "datasets/sweets.yml")
    @Transactional
    void 存在しないIDを指定した場合は空のOptionalが返ること () {
        Optional<Sweet> sweets = sweetMapper.findById(999);
        assertThat(sweets).isEmpty();
    }

    @Test
    @DataSet(value = "datasets/sweets.yml")
    @Transactional
    void 新しいスイーツが登録できること () {
        Sweet sweet = new Sweet("もみじ饅頭", "にしき堂", 1080, "広島県");
        int result = sweetMapper.insert(sweet);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DataSet(value = "datasets/sweets.yml")
    @Transactional
    void 存在するIDにスイーツが正しく更新できること() {
        Optional<Sweet> optionalSweet = sweetMapper.findById(1);
        Sweet sweet = optionalSweet.orElseThrow(() -> new AssertionError("Sweet not found"));

        sweet.setName("もみじ饅頭");
        sweet.setCompany("にしき堂");
        sweet.setPrice(1080);
        sweet.setPrefecture("広島県");

        int result = sweetMapper.update(sweet);

        assertThat(result).isEqualTo(1);

        Optional<Sweet> updatedSweetOptional = sweetMapper.findById(1);
        Sweet updatedSweet = updatedSweetOptional.orElseThrow(() -> new AssertionError("Sweet not found"));

        assertThat(updatedSweet.getName()).isEqualTo("もみじ饅頭");
        assertThat(updatedSweet.getCompany()).isEqualTo("にしき堂");
        assertThat(updatedSweet.getPrice()).isEqualTo(1080);
        assertThat(updatedSweet.getPrefecture()).isEqualTo("広島県");
    }

    @Test
    @DataSet(value = "datasets/sweets.yml")
    @Transactional
    void 存在するIDのスイーツが正しく削除できること () {
        Optional<Sweet> optionalSweet = sweetMapper.findById(1);
        Sweet sweets = optionalSweet.orElseThrow(() -> new AssertionError("Sweet not found"));

        sweetMapper.delete(sweets.getId());

        Optional<Sweet> deletedSweet = sweetMapper.findById(1);
        assertThat(deletedSweet).isEmpty();
    }
}
