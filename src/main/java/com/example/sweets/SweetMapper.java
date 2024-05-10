package com.example.sweets;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SweetMapper {
    @Select("SELECT * FROM sweets")
    List<Sweet> findAll();

    @Select("SELECT * FROM sweets WHERE id = #{id}")
    Optional<Sweet> findById(int id);
}
