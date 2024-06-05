package com.example.sweets.service;

import com.example.sweets.Sweet;
import com.example.sweets.SweetMapper;
import com.example.sweets.SweetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class SweetServiceTest {

    @InjectMocks
    private SweetService sweetService;

    @Mock
    private SweetMapper sweetMapper;

    @Test
    public void 指定したIDの名前を取得できる() {

        doReturn(Optional.of(new Sweet(1, "博多通りもん", "明月堂", 720, "福岡県")));

        Sweet actual = sweetService.findSweet(1);

        assertThat(actual).isEqualTo(new Sweet(1, "博多通りもん", "明月堂", 720, "福岡県"));
    }

}
