package com.tra.repository;

import com.tra.entity.Person;
import com.tra.entity.PersonRole;
import com.tra.entity.Role;

import java.util.List;

public interface IPersonRole {

    public Person getPersonAndRoleByUid(int uId);

    public void insertPerson(Person person);

    public void queryPersonById(int id);

    public void deletePersonById(int id);

    public void insertPersonRole(List<PersonRole> personRoles);
}
