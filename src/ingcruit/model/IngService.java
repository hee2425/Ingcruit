package ingcruit.model;

import java.util.List;

import ingcruit.vo.AppVO;
import ingcruit.vo.IngVO;
import ingcruit.vo.MemVO;

public class IngService {
	IngDAO ingDao = new IngDAO();
	
	public List<IngVO> selectAll() {
		return ingDao.selectAll();
	}
	
	//회원가입(신규 회원 등록)
	public String memInsert(MemVO mem) {
		int result = ingDao.memInsert(mem);
		return result>0?"회원가입완료!!" : "회원가입실패!!";
	}
	
	//아이디체크
	public String IDcheck(String memid){
		int result = ingDao.IDcheck(memid);
		String s_result = "";
		if(result<=0) s_result = "없는 아이디 입니다.";
		return s_result; 
	}
	
	//비밀번호 체크
	public String PWcheck(String memid, String mempw){
		int result = ingDao.PWcheck(memid, mempw);
		String s_result = "";
		if(result<=0) s_result = "잘못된 비밀번호입니다.";
		return s_result;
	}
	
	
	//로그인
	public MemVO login(String memid, String mempw) {
		return ingDao.login(memid, mempw);
	}
	
	//기업크기별 검색
	public List<IngVO> selectByGrade(int comid){
		return ingDao.selectByGrade(comid);
	}
	
	//직무별 검색
	public List<IngVO> selectByjob(String jobid){
		return ingDao.selectByjob(jobid);
	}
	
	//FullTime/PartTime 검색
	public List<IngVO> selectByFP(char fp){
		return ingDao.selectByFP(fp);
	}
	
	//관리자 공고 업로드
	public String ingUpload(IngVO ing) {
		int result = ingDao.ingUpload(ing);
		return result>0?"공고업로드완료!!" : "공고업로드실패!!";
	}
	
	//관리자 공고 수정
	public String ingUpdate(IngVO ing) {
		int result = ingDao.ingUpdate(ing);
		return result>0?"공고수정완료!!" : "공고수정실패!!";
	}
	
	public IngVO selectById(int ingid) {
		return ingDao.selectById(ingid);
	}

	
	///관리자 - 공고 삭제
	public String ingDelete(int ingid) {
		int result = ingDao.ingDelete(ingid);
		return result > 0?"공고삭제성공" : "공고삭제실패";
	}
	
	//내 직무 맞춤 공고 추천
	public List<IngVO> selectByMyJob(String jobid){
		return ingDao.selectByMyJob(jobid);
	}
	
	//지원insert
	public String applyInsert(String mid, int rid) {
		int result = ingDao.applyInsert(mid, rid);
		return result>0?"지원완료!!" : "지원실패!!";
	}
	
	//회사 이름 선택해 공고 검색
	public List<IngVO> selectByComname(String comname){
		return ingDao.selectByComname(comname);
	}
	
	//지원select
	public List<AppVO> selectApply(String mid){
		return ingDao.selectApply(mid);
	}
}
