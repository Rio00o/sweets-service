package com.example.sweets;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SweetMapper {
    @Select("SELECT * FROM sweets")
    List<Sweet> findAll();

    @Select("SELECT * FROM sweets WHERE id = #{id}")
    Optional<Sweet> findById(int id);

    @Select("SELECT * FROM sweets WHERE name = #{name}")
    Optional<Sweet> findByName(String name);

    @Insert("INSERT INTO sweets (name, company, price, prefecture) VALUES (#{name}, #{company}, #{price}, #{prefecture})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Sweet sweet);

    @Update("UPDATE sweets SET name = #{name}, company = #{company}, price = #{price}, prefecture = #{prefecture} WHERE id = #{id}")
    void update(Sweet sweet);

    @Delete("DELETE FROM sweets WHERE id = #{id}")
    void delete(Integer id);

}
