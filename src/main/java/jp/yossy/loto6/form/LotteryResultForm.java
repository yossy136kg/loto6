package jp.yossy.loto6.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class LotteryResultForm implements Serializable {

	private static final long serialVersionUID = 1330043957072942381L;

	private String minId;

	private String maxId;

	private String minLotteryDate;

	private String maxLotteryDate;


}
