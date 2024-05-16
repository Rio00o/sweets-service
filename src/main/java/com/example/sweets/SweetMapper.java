package com.example.sweets;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SweetMapper {
    @Select("SELECT * FROM sweets")
    List<Sweet> findAll();

    @Select("SELECT * FROM sweets WHERE id = #{id}")
    Optional<Sweet> findById(int id);

    @Insert("INSERT INTO sweets (name, company, price, prefecture) VALUES (#{name}, #{company}, #{price}, #{prefecture})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Sweet sweet);

}
