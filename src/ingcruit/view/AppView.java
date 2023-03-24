package ingcruit.view;

import java.util.List;

import ingcruit.vo.AppVO;


public class AppView {
	public static void print(List<AppVO> applist) {
		System.out.println("===============내 지원 정보==================");
		for(AppVO app: applist) {
			System.out.println(app);
		}
	}
	
	public static void print(String str) {
		System.out.println("[알림] 지원완료");
	}
	
}
