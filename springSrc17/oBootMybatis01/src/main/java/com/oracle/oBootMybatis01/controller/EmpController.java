package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.model.Member1;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
//logger 쓰는 것!!!
@Slf4j
public class EmpController {
   private final EmpService es;
   private final JavaMailSender mailSender;
   
   @RequestMapping(value = "listEmpStart")
   public String listEmpStart(Emp emp, Model model) {
      System.out.println("EmpController listEmp Start...");
      //21명 -> 3page가 있어야 한다.
      int totalEmp = es.totalEmp();
      String currentPage = "1"; 
      
      //Paging 작업
      Paging page = new Paging(totalEmp, currentPage);
      
      //Parameter emp --> Page만 추가 Setting
      emp.setStart(page.getStart());   //시작 시 1
      emp.setEnd(page.getEnd());       //시작 시 10
      
      List<Emp> listEmp = es.listEmp(emp);
      System.out.println("EmpController list listEmp.size()-> "+listEmp.size());
      
      model.addAttribute("totalEmp", totalEmp);
      model.addAttribute("listEmp",listEmp);
      model.addAttribute("page",page);
      // ViewResolver에 의해 list.jsp로 이동
      return "list";
   }
   
   @RequestMapping(value = "listEmp")
   public String listEmp(Emp emp, Model model) {
      System.out.println("EmpController listEmp Start...");
      //21명 -> 3page가 있어야 한다.
      int totalEmp = es.totalEmp();
      
      //Paging 작업
      Paging page = new Paging(totalEmp, emp.getCurrentPage());
      
      //Parameter emp --> Page만 추가 Setting
      emp.setStart(page.getStart());     //시작 시 1
      emp.setEnd(page.getEnd());       //시작 시 10
      
      List<Emp> listEmp = es.listEmp(emp);
      System.out.println("EmpController list listEmp.size()-> "+listEmp.size());
      
      model.addAttribute("totalEmp", totalEmp);
      model.addAttribute("listEmp",listEmp);
      model.addAttribute("page",page);
      
      // ViewResolver에 의해 list.jsp로 이동
      return "list";
   }
   
   @GetMapping(value = "detailEmp")
   public String detailEmp(Emp emp1, Model model) {
      System.out.println("EmpController Start detailEmp...");
//      1. EmpService안에 detailEmp method 선언
//         1) parameter : empno
//         2) Return      Emp
//
//      2. EmpDao   detailEmp method 선언 
////                          mapper ID   ,    Parameter
//      emp = session.selectOne("tkEmpSelOne",    empno);
//      System.out.println("emp-> "+emp1);
      
      Emp emp = es.detailEmp(emp1.getEmpno());
      
      System.out.println("emp-> "+emp1);
      model.addAttribute("emp", emp);   
      
      return "detailEmp";
   }
   
   @GetMapping(value = "updateFormEmp")
   public String updateFormEmp(Emp emp1, Model model) {
	   System.out.println("EmpController Start updateForm...");
	   Emp emp = es.detailEmp(emp1.getEmpno());
	   System.out.println("EmpController UpdateFormEmp Emp-->" + emp);

	   // 문제 
	   // 1. DTO  String hiredate
	   // 2.View : 단순조회 OK ,JSP에서 input type="date" 문제 발생
	   // 3.해결책  : 년월일만 짤라 넣어 주어야 함
	   
	   String hiredate = "";
	   if (emp.getHiredate() !=null) {
		   hiredate = emp.getHiredate().substring(0,10);
		   emp.setHiredate(hiredate);
	   }
	   System.out.println("hiredate" + emp.getHiredate());
	   model.addAttribute("emp", emp);
	   
	   
	   return "updateFormEmp";
   }
   
   @PostMapping(value ="updateEmp")
   public String updateEmp(Emp emp, Model model) {
	   log.info("updateEmp start...");
//     1. EmpService안에 updateEmp method 선언
//     1) parameter : Emp
//     2) Return      updateCount (int)
//
//  2. EmpDao updateEmp method 선언
////                             mapper ID   ,    Parameter
//  updateCount = session.update("tkEmpUpdate",   emp);
	   int updateCount = es.updateEmp(emp);
	   System.out.println("empController es.updateEmp updateCount" + updateCount);
	   model.addAttribute("uptCnt",updateCount); // test Controller 간 data 전달
	   model.addAttribute("kk3","Message Test");
	   
	   return "forward:listEmp";
	   //return "redirect:listEmp";
   }
   
   @RequestMapping(value = "writeFormEmp")
   public String wirteFormEmp(Model model) {
       System.out.println("empController writeFormEmp start...");
	   List <Emp> empList = es.listManager();
	   //1. service => listManager
	   //2. Dao => listManger
	   //3. mapper -> tkSelectManager
	   System.out.println("EmpController writeForm empList size => " +empList.size());
	   model.addAttribute("empMngList",empList);
	   List<Dept> deptList = es.deptSelect();
	   model.addAttribute("deptList",deptList);
	   System.out.println("EmpController writeForm deptList size => " +deptList.size());
	   return "writeFormEmp";
   }
   
   @PostMapping(value = "writeEmp")
   public String writeEmp(Emp emp, Model model) {
	   System.out.println("EmpController Start writeEmp...");
	   int insertResult = es.insertEmp(emp);
	   if(insertResult>0) return "redirect:listEmp";
	   else {
		   model.addAttribute("msg","입력 실패 확인해 보세요");
		   return "forward:writeFormEmp";
	   }
	   
   }
   @RequestMapping(value = "writeFormEmp3")
   public String wirteFormEmp3(Model model) {
	   System.out.println("empController writeFormEmp3 start...");
	   List <Emp> empList = es.listManager();
	   //1. service => listManager
	   //2. Dao => listManger
	   //3. mapper -> tkSelectManager
	   System.out.println("EmpController writeForm empList size => " +empList.size());
	   model.addAttribute("empMngList",empList);
	   List<Dept> deptList = es.deptSelect();
	   model.addAttribute("deptList",deptList);
	   System.out.println("EmpController writeForm deptList size => " +deptList.size());
	   return "writeFormEmp3";
   }
   
// Validation시 참조
   @PostMapping(value = "writeEmp3")
   public String writeEmp3(@ModelAttribute ("emp") @Valid Emp emp
						 , BindingResult result
					     , Model model) {
	   System.out.println("EmpController Start writeEmp...");
	   
	// Validation 오류시 Result
	  if(result.hasErrors()) {
		  System.out.println("EmpController writeEmp3 hasErrors...");
		  model.addAttribute("msg","BindingResult 입력 실패 확인해 보세요");
		  return "forward:writeFormEmp3";
	  }
	   
	   int insertResult = es.insertEmp(emp);
	   if(insertResult>0) return "redirect:listEmp";
	   else {
		   model.addAttribute("msg","입력 실패 확인해 보세요");
		   return "forward:writeFormEmp3";
	   }
	   
   }
     
   @GetMapping(value = "confirm")
   public String confirm(Emp emp1, Model model) {
	   Emp emp = es.detailEmp(emp1.getEmpno());
	   model.addAttribute("empno",emp1.getEmpno());
	   if(emp != null) {
		   System.out.println("empController confirm 중복된 사번..");
		   model.addAttribute("msg","중복된 사번입니다.");
//		   return "forward:writeFormEmp";
	   }else {
		   System.out.println("empController confirm 사용 가능한 사번..");
		   model.addAttribute("msg","사용 가능한 사번입니다");
//		   return "forward:writeFormEmp";
	   }
	   return "forward:writeFormEmp";
   }
   
   @RequestMapping(value = "deleteEmp")
   public String deleteEmp(Emp emp, Model model) {
       System.out.println("EmpController start delete");
	   int result = es.deletEmp(emp.getEmpno());
	   return "redirect:listEmp";
	   
	// Controller -->  deleteEmp    1.parameter : empno
		// name -> Service, dao , mapper
		// return -> listEmp
   }
   
   @RequestMapping(value = "listSearch3")
   public String listSearch3(Emp emp, Model model) {
	   System.out.println("EmpController Start listEmp...");
	   //Emp 전체 Count
	   int totalEmp = es.condTotalEmp(emp);
	   System.out.println("EmpController listSearch3 Emp..." + emp);
	   Paging page = new Paging(totalEmp, emp.getCurrentPage());
	   emp.setStart(page.getStart());
	   emp.setEnd(page.getEnd());
	   System.out.println("EmpController listSearch3 page..." + page);
	   
	   List<Emp> listSearchEmp = es.listSearchEmp(emp);
	   System.out.println("EmpController listSearch3 listSearchEmp.size()=>" + listSearchEmp.size());
	   model.addAttribute("totalEmp",totalEmp);
	   model.addAttribute("listEmp",listSearchEmp);
	   model.addAttribute("page",page);
	   
	   return "list";
   }
   
   @GetMapping(value = "listEmpDept")
   public String listEmpDept(Model model) {
       System.out.println("EmpController listEmpDept Start...");
	   List<EmpDept> listEmpDept = es.listEmpDept();
	   model.addAttribute("listEmpDept", listEmpDept);
	   return "listEmpDept";
   }
	// Service ,DAO -> listEmpDept
	// Mapper만 ->tkListEmpDept
   // Mapper만 ->EmpDept.xml(tkListEmpDept)
   
   @RequestMapping(value = "mailTransport")
   public String mailTransport(HttpServletRequest request, Model model) {
       System.out.println("mailSending...");
       String tomail = "lia47o@naver.com"; 	//받는사람 이메일
       System.out.println(tomail);
       String setform = "lia47o@hanyang.ac.kr";
       String title ="mailTransport입니다"; 	// 제목
       
       try {
    	   // Mime<MultiPurpose internet mail extension> 전자우편 인터넷 표준 format
    	   MimeMessage message = mailSender.createMimeMessage();
    	   MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    	   messageHelper.setFrom(setform);	//보내는 사람 생략하면 정상작동을 안 한다
    	   messageHelper.setTo(tomail); 	//받는 사람
    	   messageHelper.setSubject(title); //메일 제목은 생략 가능
    	  
    	   String tempPassword = (int)(Math.random()*99999)+1+"";
    	   messageHelper.setText("임시비밀번호입니다. : " + tempPassword); //메일 내용
    	   System.out.println("임시 비밀번호입니다. : " +tempPassword);
    	   
    	   mailSender.send(message);
    	   model.addAttribute("check",1);
       } catch (Exception e) {
    	   System.out.println("mailTransport e.getMessage()->" +e.getMessage());
    	   model.addAttribute("check",2);
	}
       return "mailResult";
   }
   	// Procedure Test 입력 화면
	   @RequestMapping(value = "writeDeptIn")
	   public String writeDeptIn(Model model) {
		   System.out.println("writeDeptIn Start");
		   return "writeDept3";
	   }
	   
	   // CallbyReferrence
	   //Procedure 통한 Dept 입력 후 vo 전달
	  @PostMapping(value = "writeDept")
	  public String writeDept(DeptVO deptVO, Model model){
		  es.insertDept(deptVO);
		  if(deptVO == null) {
			  System.out.println("deptVO NULL");
		  }else {
			  System.out.println("deptVO.getOdeptno()"+deptVO.getOdeptno());
			  System.out.println("deptVO.getOdname()" +deptVO.getOdname());
			  System.out.println("deptVO.getOloc()"   +deptVO.getOloc());
			  model.addAttribute("msg","정상입력되었습니다");
			  model.addAttribute("deptVO",deptVO);
		  }
		  return "writeDept3";
	  }
	  
	  @GetMapping(value = "writeDeptCursor")
	  public String writeDeptCursor(Model model) {
		  System.out.println("EmpController writeDeptCursor Start...");
		  HashMap<String, Object> map = new HashMap<String,Object>();
		  map.put("sDeptno", 10);
		  map.put("eDeptno", 55);
		  
		  es.selListDept(map);
		  List<Dept> deptLists = (List<Dept>) map.get("dept");
		  for(Dept dept: deptLists) {
			  System.out.println("writeDeptCursor dept->" +dept);
		  }
		  System.out.println("deptListSize -> " +deptLists.size());
		  model.addAttribute("deptList", deptLists);
		  
		  return "writeDeptCursor";
	  }
	  
	//interCeptor 시작 화면
	   @RequestMapping(value = "interCeptorForm")
	   public String interCeptorForm() {
	      System.out.println("EmpController interCeptorForm start...");
	      return "interCeptorForm";
	   }
	   
	   // 2. interCeptor Number2
	   @RequestMapping(value = "interCeptor")
	   public String interCeptor(Member1 member1, Model model) {
	      System.out.println("EmpController interCeptor Test Start");
	      System.out.println("EmpController interCeptor id-> "+member1.getId());
	      // 존재 : 1, 비존재 : 0
	      int memCnt = es.memCount(member1.getId());
	      
	      System.out.println("EmpController interCeptor memCnt -> "+memCnt);
	      
	      model.addAttribute("id", member1.getId());
	      model.addAttribute("memCnt", memCnt);
	      System.out.println("EmpController interCeptor Test End");
	      
	      return "interCeptor";   //User 존재하면 User 이용 조회 Page
	   }
	   
	   //SampleInterCeptor 내용을 받아 처리
	   @RequestMapping(value = "doMemberWrite")
	   public String doMemberWrite(Model model, HttpServletRequest request) {
	      String ID = (String) request.getSession().getAttribute("ID");
	      System.out.println("doMemberWrite 부터 하세요");
	      model.addAttribute("id", ID);
	      return "doMemberWrite";
	   }
	   
	   //interCeptor 진행 Test
	   @RequestMapping(value = "doMemberList")
	   public String doMemberList(Model model, HttpServletRequest request) {
	      String ID = (String) request.getSession().getAttribute("ID");
	      System.out.println("doMemberList Test Start ID -> "+ID);
	      Member1 member1 = null;
	      // Member1 List Get Service
	      // Service, DAO --> listMem
	      // Mapper --> listMember1
	      // Member1 모든 Row Get
	      List<Member1> listMem = es.listMem(member1);
	      model.addAttribute("ID", ID);
	      model.addAttribute("listMem", listMem);
	      return "doMemberList";
	   }
	   
	   @RequestMapping(value = "ajaxForm")
	   public String ajaxForm(Model model) {
		   System.out.println("ajaxForm Start...");
		   return "ajaxForm";
	   }
	   
	   // controller만 가지고 있기 때문에 responseBody를 가지고 와서 httpMessageConverter로 넘어간다
	   @ResponseBody
	   @RequestMapping(value = "getDeptName")
	   public String getDeptName (Dept dept,Model model) { 
		   System.out.println("EmpController deptno" + dept.getDeptno());
		   String deptName = es.deptName(dept.getDeptno());
		   System.out.println("EmpController deptName => " + deptName);
		   return deptName;
	   }
	   
	   //ajax list Test
	   @RequestMapping(value = "listEmpAjaxForm")
	   public String listEmpAjaxForm(Model model) {
		   Emp emp = new Emp();
		   System.out.println("Ajax List Test Start...");
		   emp.setStart(1);
		   emp.setEnd(10);
		   
		   List<Emp> listEmp = es.listEmp(emp);
		   System.out.println("EmpController listEmpAjax listEmp.size()->" + listEmp.size());
		   model.addAttribute("listEmp",listEmp);
		   model.addAttribute("result","kkk");
		   return "listEmpAjaxForm";
	   }
	   
	   @ResponseBody
	   @RequestMapping(value = "empSerializeWrite")
//	   public Map<String, Object> empSerializeWrite(@Valid Emp emp){
	   public Map<String, Object> empSerializeWrite(@RequestBody @Valid Emp emp){
		   System.out.println("EmpController empSerializeWrite Start...");
		   System.out.println("EmpController empSerializeWrite emp ... " + emp);
		   int writeResult = 1;
		   
		   Map<String, Object> resultMap = new HashMap<>();
		   System.out.println("EmpController empSerializeWrite writeResult => " +writeResult);
		   resultMap.put("writeResult", writeResult);
		   return resultMap;
	   }
	   
	   @RequestMapping(value = "listEmpAjaxForm2")
	   	public String listEmpAjaxForm2(Model model) {
		   System.out.println("listEmpAjaxForm2 Start...");
		   Emp emp = new Emp();
		   System.out.println("Ajax List Test Start...");
		   emp.setStart(1);
		   emp.setEnd(24);
		   List<Emp> listEmp = es.listEmp(emp);
		   model.addAttribute("listEmp",listEmp);
		   return "listEmpAjaxForm2";
	   }
	   
	   @RequestMapping(value = "listEmpAjaxForm3")
	    public String listEmpAjaxForm3(Model model) {
		   System.out.println("listEmpAjaxForm3 Start...");
		   Emp emp = new Emp();
		   System.out.println("Ajax List Test Start...");
		   emp.setStart(1);
		   emp.setEnd(24);
		   List<Emp> listEmp = es.listEmp(emp);
		   System.out.println("EmpController listEmpAjax3 listEmp.size()->" + listEmp.size());
		   model.addAttribute("listEmp",listEmp);
		   return "listEmpAjaxForm3";
	   }
	   
	   @ResponseBody
	   @RequestMapping(value = "empListUpdate")
	   	public Map<String, Object> empListUpdate(@RequestBody @Valid List<Emp> listEmp) {
		   System.out.println("EmpController empListUpdate Start...");
		   int updateResult = 1;
		   
		   for (Emp emp : listEmp) {
			   System.out.println("EmpController empListUpdate emp -> " + emp);
			   // int writeResult = kkk.listUpdateEmp(emp);
		   }
		   Map<String, Object> resultMap = new HashMap<>();
		   resultMap.put("updateResult", updateResult);
		   return resultMap;
	   }
	   
	   @ResponseBody
	   @RequestMapping(value = "transactionInsertUpdate")
	   public String transactionInsertUpdate(Emp emp, Model model) {
		   System.out.println("EmpController transactionInsertUpdate Start...");
		   	// memberInsert 성공과 실패
		   int returnMember = es.transactionInsertUpdate();
		   System.out.println("EmpController transactionInsertUpdate returnMember" + returnMember);
		   String returnMemberString = String.valueOf(returnMember);
		   
		   return returnMemberString;
	   }
	
}
