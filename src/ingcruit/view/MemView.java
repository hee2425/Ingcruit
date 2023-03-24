package ingcruit.view;

import ingcruit.vo.MemVO;

public class MemView {
	public static void print(MemVO mem) {
		System.out.println("--------------회원정보----------------");
		if(mem==null) {
			print("회원이 존재하지 않습니다.");
		}else {
			System.out.println(mem);

		}
	}
	
	public static void print(String message) {
		System.out.println("[알림]" + message);
	}
}
