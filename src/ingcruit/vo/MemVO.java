package ingcruit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class MemVO {
	private String member_id;
	private String member_name;
	private String member_pw;
	private String job_id;
	private String phonenumber;
}
