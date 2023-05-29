package com.javaex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhoneDao;
import com.javaex.service.PhoneService;
import com.javaex.vo.PersonVo;


//setting 에 컨트롤러 위치를 알려줘야하고 여기 컨트롤러 왔더니 @가 써있어야 조사대상이 되어 찾는다. 메서드마다 맵핑을 하나씩 한다. 

@Controller
public class PhoneController {
	
	//ds한테 NEW PhoneDao(); 해달라고 하기.
	@Autowired
	private PhoneService phoneService;

	
	@Autowired
	private PhoneDao phoneDao;
	
	//전체 리스트 
		@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
		public String list(Model model) {
			System.out.println("PhoneController.list()");//테스트
			
			List<PersonVo> personList = phoneService.getPersonList();
			System.out.println(personList);
			
			//List<PersonVo> personList = phoneDao.getPersonList();
			//System.out.println("PhoneController.list()");
					
			model.addAttribute("pList", personList);//이름은 pList고 리스트가 담겨있는건 personList다. 
					
			return "list";
		}
		
		//등록폼 
				@RequestMapping("/writeForm")//이런주소로 요청이오면 이 메서드로 응답하라는 의미): 이게 HandlerMapping에 저장이된다. 
				public String writeForm() {
					System.out.println("writeForm()");
					return "writeForm";//이 담당자를 찾아가세요. 하는것.
				}			
			
		
		//등록 case2 : 파라미터가 너무 많을 때
		@RequestMapping(value="/write", method= {RequestMethod.POST, RequestMethod.GET})//get방식으로 보냈
		public String write(@ModelAttribute PersonVo personVo) { //하나하나 꺼내지 않고 바로 VO에 담아달라고 한다. 이건 Dispatcher Servlet이 하는거다. 						
			System.out.println("PhoneController.write()"); 
			
			phoneService.writePerson(personVo);
			
			return "redirect:/list";
		}
		
		
	//전화번호 등록2(파라미터로 뽑아서 MAP써서 INSERT하기)
	@RequestMapping(value="/write2", method={RequestMethod.GET, RequestMethod.POST})
	public String write2(@RequestParam("name") String name,
						 @RequestParam("hp") String hp,
						 @RequestParam("company") String company) {
		System.out.println("write2()");
		
		phoneDao.personInsert(name, hp, company);//파라미터로 뽑으면 DAO에서 객체 3개짜리 오버라이드 메서드로 수행
		return "redirect:/list";
	}
	
	//수정 폼 (id값을 주고 Vo 데이터 가져오기)
	@RequestMapping(value="/modifyForm", method={RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(@RequestParam("personId") int personId, Model model) {
		System.out.println("modifyForm");
		
		PersonVo personVo = phoneDao.getPerson(personId);	
		model.addAttribute("personVo", personVo);
		return "modifyForm";
	}
	//수정 폼2 (id값을 주고 MAP데이터 가져오기)
	@RequestMapping(value="/modifyForm2", method={RequestMethod.GET, RequestMethod.POST})
	public String modifyFrom2(@RequestParam("personId") int personId, Model model) {
		System.out.println("modifyFrom2()");
		
		Map<String, Object> personMap = phoneDao.getPerson2(personId);
		model.addAttribute("Map", personMap);//맵에 담은걸 모델에 담아 jsp로 보냄.
		return "modifyForm2";
	}
	
	
	//수정 작동(Vo 값넘기고 r. list가져오기)
		@RequestMapping(value="/update", method={RequestMethod.GET, RequestMethod.POST})
		public String update(@ModelAttribute PersonVo personVo) {

			System.out.println("update()");
			System.out.println("personVo");
			
			phoneDao.personUpdate(personVo);			
			
			//리다이렉트 안쓸 시
//			List<PersonVo> pList = PhoneDao.getPersonList();
//			model.addAttribute("personList", pList);			
//			return "WEB-INF/views/list.jsp";			 
			return "redirect:/list"; //리다이렉트랑 포워드 두개를 쓰고 인터넷주소를 써야된다.
		}
		
	
		
		//삭제
		@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
		public String delete(@RequestParam("personId") int personId) {
			System.out.println("delet()");
			
			phoneService.deletePerson(personId);
			
			return "redirect:/list";
			}
	
	
	
	/* case1: 파라미터 하나씩 꺼내야할때
	@RequestMapping(value="/write", method= {RequestMethod.POST, RequestMethod.GET})//get방식으로 보냈
	public String write(@RequestParam("name")String name, 
			            @RequestParam("hp") String hp, 
			            @RequestParam("company") String company) {
		System.out.println("write()");
		PersonVo personVo = new PersonVo(name, hp, company); //생성자 3개짜리 담을 수 있는 Vo메서드도 만들어놔야함.
		System.out.println(personVo);
		
		PhoneDao PhoneDao = new PhoneDao();//묶었으면 DAO에 저장해야되므로 dao생성자만듬
		PhoneDao.personInsert(personVo);
		
		//리스트로 리다이렉트 하기.
		
		return "";
	}*/
	
	
	
	/*//@PathVariable 사용
	@RequestMapping(value="/modifyForm/{no1}/{no2}", method= {RequestMethod.POST, RequestMethod.GET})
	public String modifyForm2(@PathVariable("no1") int no1, @PathVariable("no2") int no2) {
		System.out.println("modifyForm2()");
		System.out.println(no1);
		System.out.println(no2);
		return "";
	}*/
			}


