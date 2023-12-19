package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entities.CredentialEntity;
import com.udacity.jwdnd.course1.cloudstorage.entities.CredentialEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<CredentialEntity> getAllCredentials(int userId);

    @Insert("INSERT INTO CREDENTIALS (url,username,key,password, userid) VALUES(#{url}, #{username},#{key},#{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void createCredential(CredentialEntity credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    CredentialEntity getCredentialById(int credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, `key`=#{key}, password= #{password} WHERE credentialid = #{credentialId}")
    void updateCredential(CredentialEntity credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredential(Integer credentialId);
}
