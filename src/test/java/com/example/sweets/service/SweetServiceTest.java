package com.example.sweets.service;

import com.example.sweets.Sweet;
import com.example.sweets.SweetMapper;
import com.example.sweets.SweetNotFoundException;
import com.example.sweets.SweetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.util.Assert.isInstanceOf;

class SweetServiceTest {

    @InjectMocks
    private SweetService sweetService;

    @Mock
    private SweetMapper sweetMapper;

    @BeforeEach
    void setUp() {
        sweetMapper = mock(SweetMapper.class);
        sweetService = new SweetService(sweetMapper);
    }


    @Test
    public void 存在するスイーツのIDを指定したとき正常にスイーツが返されること() throws Exception {
        doReturn(Optional.of(new Sweet(1, "博多通りもん", "明月堂", 720, "福岡県"))).when(sweetMapper).findById(1);

        Sweet actual = sweetService.findById(1);
        assertThat(actual).isEqualTo(new Sweet(1, "博多通りもん", "明月堂", 720, "福岡県"));
    }

    @Test
    public void すべてのスイーツが取得できること() {
        List<Sweet> sweet  = List.of(
                new Sweet(1, "博多通りもん", "明月堂", 720, "福岡県"),
                new Sweet(2, "萩の月", "菓匠三全", 1500, "宮城県"),
                new Sweet(3, "白い恋人", "石屋製菓", 1036, "北海道"),
                new Sweet(4, "東京ばな奈", "東京ばな奈ワールド", 1198, "東京都")
        );
        doReturn(sweet).when(sweetMapper).findAll();
        List<Sweet> actual = sweetService.findAll();
        assertThat(actual).isEqualTo(sweet);
        verify(sweetMapper).findAll();
    }


    @Test
    public void 存在しないIDを指定した場合は例外が発生すること() {
        doReturn(Optional.empty()).when(sweetMapper).findById(0);
        assertThatThrownBy(() -> sweetService.findSweet(0))
            .isInstanceOf(SweetNotFoundException.class);
        verify(sweetMapper).findById(0);
    }

    @Test
    public void 新しいスイーツを登録すること () {
        Sweet sweet = new Sweet("もみじ饅頭", "にしき堂", 1080, "広島県");
        assertThat(sweetService.insert("もみじ饅頭", "にしき堂", 1080, "広島県")).isEqualTo(sweet);
        verify(sweetMapper).insert(sweet);
    }

    @Test
    public void 存在するスイーツを削除すること () {
        Integer validId = 1;
        Sweet existingSweet = new Sweet(1, "博多通りもん", "明月堂", 720, "福岡県");
        when(sweetMapper.findById(1)).thenReturn(Optional.of(existingSweet));
        sweetService.delete(validId);
        verify(sweetMapper).delete(validId);
    }

    @Test
    public void 指定したIDにスイーツがない場合は削除できないこと () {
        Integer invalidId = 999;
        when(sweetMapper.findById(invalidId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(SweetNotFoundException.class, () -> {
            sweetService.delete(999);
        });

        assertEquals("Sweet not found", exception.getMessage());
        verify(sweetMapper, never()).delete(invalidId);
    }
}
