package com.wm.mappers;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.wm.model.Stub;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StubsDatabaseMapper {

    @Select("SELECT * FROM stubs")
    public List<Stub> findAll();

    @Select("SELECT * FROM stubs where team=#{team}")
    public List<Stub> findAllForTeam(String team);

    @Insert("INSERT into stubs(uuid,stubMappingName,url,urlIdentifierType,priority,scenarioName,request,response,team, createRequest, createResponse,host) values(#{uuid}, #{stubMappingName}, #{url}, #{urlIdentifierType}, #{priority}, #{scenarioName}, #{request}, #{response}, #{team}, #{createRequest}, #{createResponse}, #{host})")
    public void insert(Stub stub);

    @Update("UPDATE stubs SET uuid=#{uuid}, stubMappingName=#{stubMappingName}, url=#{url}, urlIdentifierType=#{urlIdentifierType}, priority=#{priority}, scenarioName=#{scenarioName}, request=#{request}, response=#{response}, team=#{team}, createRequest=#{createRequest}, createResponse=#{createResponse}, updatedOn=CURRENT_TIMESTAMP where uuid=#{uuid}")
    public void update(Stub stub);

    @Update("UPDATE stubs SET uuid=#{uuid}, stubMappingName=#{stubMappingName}, url=#{url}, urlIdentifierType=#{urlIdentifierType}, priority=#{priority}, scenarioName=#{scenarioName}, request=#{request}, response=#{response}, team=#{team}, createRequest=#{createRequest}, createResponse=#{createResponse}, updatedOn=CURRENT_TIMESTAMP where uuid=#{oldUuid}")
    public void updateUuid(Stub stub, String oldUuid);

    @Select("SELECT * FROM stubs where uuid=#{uuid}")
    public Stub findStub(String uuid);

    @Delete("DELETE FROM stubs where uuid=#{uuid}")
    public void deleteStub(String uuid);

    @Update("UPDATE stubs SET active=0 where uuid=#{uuid}")
    public void disAbleStub(String uuid);

    @Update("UPDATE stubs SET active=1 where uuid=#{uuid}")
    public void activateStub(String uuid);
}

