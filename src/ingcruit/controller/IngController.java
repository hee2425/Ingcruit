package ingcruit.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

import ingcruit.model.IngService;
import ingcruit.view.AppView;
import ingcruit.view.IngView;
import ingcruit.view.MemView;
import ingcruit.vo.IngVO;
import ingcruit.vo.MemVO;

public class IngController {
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		IngService iService = new IngService();
		MemVO member = null;
		System.out.println("****안녕하세요! 잉크루트 입니다***");
		
		while(true) {
			System.out.println();
			System.out.println("1.로그인/로그아웃");
			System.out.println("2.회원가입");
			System.out.println("3.모든 공고 보기");
			System.out.println("4.기업 크기별 검색");
			System.out.println("5.직무별 검색");
			System.out.println("6.FullTime/PartTime 검색");
			System.out.println("7.내 직무 맞춤 공고 추천");			
			System.out.println("8.지원하기");
			System.out.println("9.내가 지원한 공고 모아보기");
			System.out.println("exit 종료");
			
			System.out.println();
			System.out.print("작업선택>>>>>>>>>>");
			
			String job = sc.next();
			if(job.equals("exit")) break;
			switch(job) {
			case "1":
				if(member!=null) {
					System.out.println(member.getMember_id()+"님 로그인되어있습니다.");
					System.out.print("로그아웃을 원하시나요?(Y/n)>>");
					String yn = sc.next();
					if(yn.equals("Y")||yn.equals("y")) {
						member = null;
						System.out.println("로그아웃 됐습니다...");
					}
				}
				else{
					System.out.print("아이디>>");
					String memid = sc.next();
					String s = iService.IDcheck(memid);  //없으면 - 없는아이디~, 있으면 ""
					String s1 = iService.PWcheck(memid, memid);
					
					if(s.length()>0) {
						System.out.println("[알림]"+s); 
						break;
					}
					System.out.print("비밀번호>>");
					String mempw = sc.next();
					if(s1.length()>0) {
						System.out.println("[알림]"+s1); 
						break;
					}
					if(memid.equals("admin") && mempw.equals("admin")) {
						System.out.println("관리자 계정입니다...");
						System.out.println("1)공고 업로드 ,2)공고 수정, 3)공고 삭제");
						int ad = sc.nextInt();
						if(ad==1) {
							IngVO ing = makeIng(sc);
							IngView.print(iService.ingUpload(ing)); break;
						}else if(ad==2) {
							System.out.print("수정할 공고ID>>");
							int rid = sc.nextInt();
							IngView.print(iService.selectById(rid));
							IngVO ing = makeIng2(sc, rid);
							IngView.print(iService.ingUpdate(ing));
							break;
							
						}else {
							System.out.print("삭제할 공고ID>>");
							int ingid = sc.nextInt();
							IngView.print(iService.selectById(ingid));
							System.out.print("삭제를 계속 진행하시겠습니까?(Y/N)>>");
							String yn = sc.next();
							if(yn.equals("Y")||yn.equals("y")) {
								IngView.print(iService.ingDelete(ingid));
							}
							break;
						}
					}else {
						member = iService.login(memid,mempw);
						MemView.print(member);
					}
				}
				
				break;
			case "2":
				MemVO mem = makeMem(sc);
				MemView.print(iService.memInsert(mem));
				break;
			case "3":
				IngView.print(iService.selectAll()); break;
			case "4":
				System.out.println("1:대기업/2:중견기업/3:중소기업/4:스타트업");
				System.out.print("번호를 입력해주세요>>");
				int comid = sc.nextInt();
				IngView.print(iService.selectByGrade(comid)); break;
			case "5":
				System.out.println("developer/sales/pm/qa/marketing/designer");
				System.out.print("직무를 입력해주세요>>");
				String jobid = sc.next();
				IngView.print(iService.selectByjob(jobid));break;
			case "6":
				System.out.println("Fulltime: f /Parttime: t ");
				System.out.print("입력하세요>>");
				char fp = sc.next().charAt(0);
				IngView.print(iService.selectByFP(fp));break;
			case "7":
				String myjobid = member.getJob_id();
				IngView.print(iService.selectByMyJob(myjobid));break;
			case "8":{
				System.out.print("지원할 회사공고 보기>>");
				String com = sc.next();
				IngView.print(iService.selectByComname(com));
				System.out.print("지원할 공고ID>>");
				int rid = sc.nextInt();
				String mid = member.getMember_id();
				AppView.print(iService.applyInsert(mid,rid));break;}
			case "9":{
				String memid = member.getMember_id();
				AppView.print(iService.selectApply(memid));break;}
				
			default:
				break;
			}
		}
	}

	private static IngVO makeIng2(Scanner sc, int rid) {
		System.out.print("1.수정할 직무>>");
		String jobid = sc.next();
		System.out.print("2.수정할 마감날짜(yyyy-mm-dd)>>");
		Date redate =  Date.valueOf(LocalDate.parse(sc.next()));
		System.out.print("3.수정할 채용인원>>");
		int rcount = sc.nextInt();
		System.out.print("4.수정할 인재상>>");
		String rtype = sc.next();
		System.out.print("5.수정할 FullTime / PartTime>>");
		char rfp = sc.next().charAt(0);
		
		IngVO ing = new IngVO();
		ing.setJob_id(jobid);
		ing.setDeadline_date(redate);
		ing.setRCount(rcount);
		ing.setType_of_talent(rtype);
		ing.setFp(rfp);
		ing.setRecruit_id(rid);
		System.out.println(ing);
		return ing;
	}

	private static IngVO makeIng(Scanner sc) {
		System.out.print("1.공고 ID>>");
		int reId = sc.nextInt();
		System.out.print("2.공고 직무>>");
		String rejob = sc.next();
		System.out.print("3.공고 마감날짜yyyy-mm-dd>>");
		//String->date(String->localDate->Date)
		Date redate =  Date.valueOf(LocalDate.parse(sc.next()));
		System.out.print("4.채용인원>>");
		int rcount = sc.nextInt();
		System.out.print("5.인재상>>");
		String rtype = sc.next();
		System.out.print("6.FullTime / PartTime>>");
		char rfp = sc.next().charAt(0);
		System.out.print("7.companyid>>");
		int rcom = sc.nextInt();
		
		IngVO ing = new IngVO();
		ing.setRecruit_id(reId);
		ing.setJob_id(rtype);
		ing.setDeadline_date(redate);
		ing.setRCount(rcount);
		ing.setType_of_talent(rtype);
		ing.setFp(rfp);
		ing.setCompany_id(rcom);
		return ing;
	}

	private static MemVO makeMem(Scanner sc) {
		System.out.print("1.신규 회원의 ID>>");
		String memberID = sc.next();
		System.out.print("2.신규 회원의 이름>>");
		String memberName = sc.next();
		System.out.print("3.신규 회원의 password>>");
		String memberpw = sc.next();
		System.out.print("4.신규 회원 준비 직무(developer/sales/pm/qa/marketing/designer)>> ");
		String jobid = sc.next();
		System.out.print("5.신규 회원의 phonenumber>>");
		String phone = sc.next();
		
		MemVO mem = new MemVO();
		mem.setMember_id(memberID);
		mem.setMember_name(memberName);
		mem.setMember_pw(memberpw);
		mem.setJob_id(jobid);
		mem.setPhonenumber(phone);
		return mem;
	}
	
}
