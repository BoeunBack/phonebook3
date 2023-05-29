package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;


@Service
public class PhoneService {
	//필드
	@Autowired
	private PhoneDao phoneDao;
	
	//생성자
	//메소드 겟, 셋
	//메소드 일반
	public List<PersonVo> getPersonList() {
		System.out.println("PhoneService.getPersonList()");
		
		List<PersonVo> personList = phoneDao.getPersonList();
		System.out.println(personList);
		
		return personList;
	}
	
	public void writePerson(PersonVo personVo) {
		System.out.println("PhonService.writePersion()");
		
		phoneDao.personInsert(personVo);
	}
	
	public void deletePerson(int personId) {
		System.out.println("PhoneService.deldtePerson();");
		System.out.println(personId);
		phoneDao.personDelete(personId);
	}

}
