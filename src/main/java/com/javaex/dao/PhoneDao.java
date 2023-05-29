package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhoneDao {
	@Autowired
	private SqlSession sqlSession;// 주소 받을 애
	
	// 사람 리스트
	public List<PersonVo> getPersonList() {
		System.out.println("PhoneDao.getPersonList()");
		
		List<PersonVo> personList = sqlSession.selectList("phonebook.selectList");// 쿼리문찾기위해 이름이 selectList인 곳을 찾아감
		System.out.println(personList);
		return personList;
	}
	
	//삭제
	public int personDelete(int personId) {
		int count = sqlSession.delete("phonebook.delete", personId);
		
		return count;		
	}
	
	//수정 폼가져오기
	public PersonVo getPerson(int personId) {
		System.out.println("getperson()");
		PersonVo personVo = sqlSession.selectOne("phonebook.selectOne", personId);
		return personVo;
	}
	
	//1명 데이터 가져오기
	public Map<String, Object> getPerson2(int personId) {
		System.out.println("PhoneDao.getperson2()");
		
		//MAP으로 리턴.
		Map<String, Object> personMap = sqlSession.selectOne("phonebook.selectOne2", personId);
		System.out.println(personMap);
		System.out.println(personMap.get("NAME")); //컬럼명이 대문자 키값으로 저장된다. 
		System.out.println(personMap.get("PERSON_ID"));
		return personMap;
	}
	
	
	//1명의 데이터수정
	public int personUpdate(PersonVo personVo) {
		return sqlSession.update("phonebook.update", personVo);		
	}
	

	// 사람 등록
	public int personInsert(PersonVo personVo) {
		System.out.println("PhoneDao.personInsert(vo)");
		System.out.println(personVo);
		int count = sqlSession.insert("phonebook.insert", personVo);
		
		return count;// 이건 5개중 1개 insert쓴것. personVo는 파라미터 주소를 주는것		
	}
	
	//사람 등록2
	public int personInsert(String name, String hp, String company) {
		System.out.println("PhoneDao.personInsert()");
		System.out.println(name);
		System.out.println(hp);
		System.out.println(company);
		
		Map<String, String> personMap = new HashMap<String, String>();//Map만든것, 앞은 키값String
		personMap.put("Name", name);
		personMap.put("Hp", hp);
		personMap.put("Company", company);
		
		sqlSession.insert("phonebook.insert2", personMap);
		
		
		return 0;
	}

}
