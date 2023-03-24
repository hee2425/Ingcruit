package ingcruit.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class AppVO {
	private String member_id;
	private int recruit_id;
	private int company_id;
	private String job_id;
	private Date deadline_date;
	private int rCount;
	private String type_of_talent;
	private char fp;
}
