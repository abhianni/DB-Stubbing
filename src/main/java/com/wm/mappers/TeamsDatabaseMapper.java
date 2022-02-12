package com.wm.mappers;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.wm.model.Stub;
import com.wm.model.Teams;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeamsDatabaseMapper {

    @Select("SELECT * FROM teams")
    public List<Teams> findAll();

    @Select("SELECT * FROM teams where enable=#{isEnable}")
    public List<Teams> find(int isEnable);
}

