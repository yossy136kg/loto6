package jp.yossy.loto6.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.yossy.loto6.repository.Loto6DataExp;
import lombok.Data;

@Data
public class LotteryResultApiData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String slctVal;

	private List<Loto6DataExp> result = new ArrayList<Loto6DataExp>();
}
