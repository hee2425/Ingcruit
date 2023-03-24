package ingcruit.view;

import java.util.List;

import ingcruit.vo.IngVO;


public class IngView {
	public static void print(List<IngVO> inglist) {
		System.out.println("================공고==================");
		for(IngVO ing: inglist) {
			System.out.println(ing);
		}
	}
	
	public static void print(String up) {
		System.out.println("확인하려면 모든 공고보기(3) 이동");
	}
	
	public static void print(IngVO ing) {
		System.out.println("---------------공고 상세 정보--------------");
		if(ing==null) {
			print("공고가 존재하지 않습니다.");
		}else {
			System.out.println(ing);
		}
		
		
	}
	

}
