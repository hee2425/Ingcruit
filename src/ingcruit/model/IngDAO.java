package ingcruit.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ingcruit.controller.ConnectionUtil;
import ingcruit.vo.AppVO;
import ingcruit.vo.IngVO;
import ingcruit.vo.MemVO;

public class IngDAO {
	
	Connection conn;
	Statement st;
	PreparedStatement pst;
	CallableStatement cst;
	ResultSet rs;
	int resultCount;
	
	
	//지원select
	public List<AppVO> selectApply(String mid){
		String sql ="select memberid, recruitid, jobid, deadlinedate, rcount, typeoftalent, fp, companyid"
				+ " from apply join recruitment using(recruitid) "
				+ " where memberid ='"+mid+"'";
		List<AppVO> applist = new ArrayList<>();
		conn = ConnectionUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				AppVO app = makeApp(rs);
				applist.add(app);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(rs, cst, conn);
		}
		return applist;
	}
	
	private AppVO makeApp(ResultSet rs) throws SQLException {
		AppVO app = new AppVO();
		app.setMember_id(rs.getString("memberid"));
		app.setRecruit_id(rs.getInt("recruitid"));
		app.setDeadline_date(rs.getDate("deadlinedate"));
		app.setRCount(rs.getInt("rCount"));
		app.setType_of_talent(rs.getString("typeoftalent"));
		app.setFp(rs.getString("fp").charAt(0));
		app.setCompany_id(rs.getInt("companyid"));
		app.setJob_id(rs.getString("jobid"));
		return app;
	}

	//지원insert
	public int applyInsert(String memid, int rid) {
		String sql = """
				insert into apply
				values(?,?)
				""";
		conn = ConnectionUtil.getConnection();
		
			try {
				pst = conn.prepareStatement(sql);
				pst.setString(1, memid);
				pst.setInt(2, rid);
				
				resultCount = pst.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				ConnectionUtil.getConnection();
			}
			return resultCount;
	}
	
	//회사 이름 선택해 공고 검색
	public List<IngVO> selectByComname(String comname){
		String sql = """
				select recruitid,companyid,jobid,companyname, deadlinedate, rcount, typeoftalent, fp 
				from recruitment join company using(companyid) 
				where lower(companyname)=lower(?)
				""";
		List<IngVO> inglist = new ArrayList<>();
		conn = ConnectionUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, comname);
			rs = pst.executeQuery();
			while(rs.next()) {
				IngVO ing = makeIng1(rs);
				inglist.add(ing);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(rs, cst, conn);
		}
		
		return inglist;
	}
	
	///관리자 - 공고 삭제
	public int ingDelete(int ingid) {
		String sql = """
				delete from recruitment
				where recruitid = ?
				""";
		conn = ConnectionUtil.getConnection();
		try {
			
			pst = conn.prepareStatement(sql);
			
			pst.setInt(1, ingid);

			resultCount = pst.executeUpdate();  
		} catch (SQLException e) {
			resultCount = -1;
			e.printStackTrace();
		} finally {
			ConnectionUtil.dbDisconnection(null, pst, conn);
		}
//		System.out.println("delete 결과 : " + resultCount);
		return resultCount; 
	}
	

	///관리자 - 공고 수정
	public int ingUpdate(IngVO ing) {
		String sql = """
				update recruitment 
				set jobid = ?, deadlinedate=?, 
				rcount=?, typeoftalent=?, fp=?
				where recruitid=?
				""";
		conn = ConnectionUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, ing.getJob_id());
			pst.setDate(2, ing.getDeadline_date());
			pst.setInt(3, ing.getRCount());
			pst.setString(4, ing.getType_of_talent());
			pst.setString(5,String.valueOf(ing.getFp()));
			pst.setInt(6, ing.getRecruit_id());
			
			resultCount = pst.executeUpdate();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionUtil.dbDisconnection(null, pst, conn);
		}
		return resultCount; 
	}
	
	//공고 id로 공고 찾기
	public IngVO selectById(int ingid) {
		IngVO ing = null;
		String sql = "select * from recruitment where recruitid="+ingid;
		conn = ConnectionUtil.getConnection();
		try {
			st = conn.createStatement();  //통로 만들고
			rs = st.executeQuery(sql);  //sql실행
			while(rs.next()) {
				ing = makeIng(rs);		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(rs, cst, conn);
		}
		return ing;
	}
	
	///관리자 - 공고 업로드
	public int ingUpload(IngVO ing) {
		String sql = """
				insert into recruitment values(?,?,?,?,?,?,?)
				""";
		conn = ConnectionUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1,ing.getRecruit_id());
			pst.setString(2,ing.getJob_id());
			pst.setDate(3, ing.getDeadline_date());
			pst.setInt(4, ing.getRCount());
			pst.setString(5, ing.getType_of_talent());
			pst.setString(6, String.valueOf(ing.getFp()));
			pst.setInt(7, ing.getCompany_id());
				
			resultCount = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(rs, cst, conn);
		}
		
		return resultCount;
	}
	
	//내 직무 맞춤 공고 추천
	public List<IngVO> selectByMyJob(String jobid){
		String sql = """
				select recruitid,companyid,jobid,companyname, deadlinedate, rcount, typeoftalent, fp 
				from recruitment join company using(companyid) 
				where jobid=?
				""";
		List<IngVO> inglist = new ArrayList<>();
		conn = ConnectionUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, jobid);
			rs = pst.executeQuery();
			while(rs.next()) {
				IngVO ing = makeIng1(rs);
				inglist.add(ing);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(rs, cst, conn);
		}
		
		return inglist;
	}
	


	//FullTime/PartTime 검색
	public List<IngVO> selectByFP(char fp){
		String sql = "select * from recruitment where lower(fp)=lower('"+fp+"')";
		List<IngVO> inglist = new ArrayList<>();
		conn = ConnectionUtil.getConnection();
		try {
			st = conn.createStatement();  //통로 만들고
			rs = st.executeQuery(sql);  //sql실행
			while(rs.next()) {
				IngVO ing = makeIng(rs);
				inglist.add(ing);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(rs, cst, conn);
		}
		return inglist;
		
	}	
	
	//직무별 검색
	public List<IngVO> selectByjob(String jobid){
		String sql = "select * from recruitment where lower(jobid) = lower('"+jobid+"')";
		List<IngVO> inglist = new ArrayList<>();
		conn = ConnectionUtil.getConnection();
		try {
			st = conn.createStatement();  //통로 만들고
			rs = st.executeQuery(sql);  //sql실행
			while(rs.next()) {
				IngVO ing = makeIng(rs);
				inglist.add(ing);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(rs, cst, conn);
		}
		return inglist;
	}
	
	//기업크기별 검색
	public List<IngVO> selectByGrade(int comid){
		String sql = "select * from recruitment where companyid = '"+comid+"'";
		List<IngVO> inglist = new ArrayList<>();
		conn = ConnectionUtil.getConnection();
		try {
			st = conn.createStatement();  //통로 만들고
			rs = st.executeQuery(sql);  //sql실행
			while(rs.next()) {
				IngVO ing = makeIng(rs);
				inglist.add(ing);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(rs, cst, conn);
		}
		return inglist;
	}
	
	//아이디체크
	public int IDcheck(String memid){
		MemVO mem = null;
		String sql = """
				select memberid from member
				where memberid = ? 
				""";
		
		conn = ConnectionUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1,memid);
			resultCount = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resultCount = -1;
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(null, pst, conn);
		}
		System.out.println();
		//System.out.println("delete 결과 : " + resultCount);
		
		return resultCount;
		
	}
	
	//비밀번호체크
	public int PWcheck(String memid, String mempw){
		MemVO mem = null;
		String sql = """
				select memberpw from member
				where memberid = ? and memberpw = ?
				""";
		
		conn = ConnectionUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1,memid);
			pst.setString(2,mempw);
			resultCount = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resultCount = -1;
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(null, pst, conn);
		}
		System.out.println();
		//System.out.println("delete 결과 : " + resultCount);
		
		return resultCount;
		
	}
	
	//로그인
	public MemVO login(String memid, String mempw){
		MemVO mem = null;
		String sql = "select * from member "
				+ "	where memberid ='"+memid+"' and memberpw ='"+mempw+"'";
		conn = ConnectionUtil.getConnection();
		try {
			st = conn.createStatement();  //통로 만들고
			rs = st.executeQuery(sql);  //sql실행
			while(rs.next()) {
				mem = makeMem(rs);		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(rs, st, conn);
		}
		
		return mem;
	}
	

	
	private MemVO makeMem(ResultSet rs2) throws SQLException {
		MemVO mem = new MemVO();
		mem.setMember_id(rs.getString("memberid"));
		mem.setMember_name(rs.getString("membername"));
		mem.setMember_pw(rs.getString("memberpw"));
		mem.setJob_id(rs.getString("jobid"));
		mem.setPhonenumber(rs.getString("phonenumber"));
		
		return mem;
	}

	//회원가입
	public int memInsert(MemVO mem) {
		String sql = """
				insert into Member values (?,?,?,?,?)
				""";
		conn = ConnectionUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1,mem.getMember_id());
			pst.setString(2,mem.getMember_name());
			pst.setString(3, mem.getMember_pw());
			pst.setString(4, mem.getJob_id());
			pst.setString(5, mem.getPhonenumber());
			
			resultCount = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(rs, cst, conn);
		}
		
		return resultCount;
	}
	
	//모두 조회
	public List<IngVO> selectAll() {
		String sql = "select * from recruitment";
		List<IngVO> inglist = new ArrayList<>();
		conn = ConnectionUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				IngVO ing = makeIng(rs);
				inglist.add(ing);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionUtil.dbDisconnection(rs, cst, conn);
		}
		
		
		return inglist;
	}

	private IngVO makeIng(ResultSet rs) throws SQLException {
		IngVO ing = new IngVO();
		
		ing.setCompany_id(rs.getInt("companyid"));
		ing.setDeadline_date(rs.getDate("deadlinedate"));
		ing.setFp(rs.getString("fp").charAt(0));
		ing.setJob_id(rs.getString("jobid"));
		ing.setRCount(rs.getInt("rCount"));
		ing.setRecruit_id(rs.getInt("recruitid"));
		ing.setType_of_talent(rs.getString("typeoftalent"));
		
		return ing;
	}
	private IngVO makeIng1(ResultSet rs) throws SQLException {
		IngVO ing = new IngVO();
		
		//select companyname, deadlinedate, rcount, typeoftalent, fp 
		//from recruitment join company using(companyid) 
		//where jobid=?
		
		ing.setRecruit_id(rs.getInt("recruitid"));
		ing.setCompany_name(rs.getString("companyname"));
		ing.setDeadline_date(rs.getDate("deadlinedate"));
		ing.setRCount(rs.getInt("rCount"));
		ing.setType_of_talent(rs.getString("typeoftalent"));
		ing.setFp(rs.getString("fp").charAt(0));
		ing.setCompany_id(rs.getInt("companyid"));
		ing.setJob_id(rs.getString("jobid"));
		
		return ing;
	}
}
