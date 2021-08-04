package jp.yossy.loto6.repository;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the loto6_data database table.
 * 
 */
@Entity
@Table(name="loto6_data")
@NamedQuery(name="Loto6Data.findAll", query="SELECT l FROM Loto6Data l")
public class Loto6Data implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int bonus;

	private int carryover;

	@Column(name="check_num")
	private String checkNum;

	@Temporal(TemporalType.DATE)
	@Column(name="lottery_date")
	private Date lotteryDate;

	private int num1;

	private int num2;

	private int num3;

	private int num4;

	private int num5;

	private int num6;

	@Column(name="prise1_cnt")
	private int prise1Cnt;

	@Column(name="prise1_money")
	private int prise1Money;

	@Column(name="prise2_cnt")
	private int prise2Cnt;

	@Column(name="prise2_money")
	private int prise2Money;

	@Column(name="prise3_cnt")
	private int prise3Cnt;

	@Column(name="prise3_money")
	private int prise3Money;

	@Column(name="prise4_cnt")
	private int prise4Cnt;

	@Column(name="prise4_money")
	private int prise4Money;

	@Column(name="prise5_cnt")
	private int prise5Cnt;

	@Column(name="prise5_money")
	private int prise5Money;

	public Loto6Data() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBonus() {
		return this.bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public int getCarryover() {
		return this.carryover;
	}

	public void setCarryover(int carryover) {
		this.carryover = carryover;
	}

	public String getCheckNum() {
		return this.checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	public Date getLotteryDate() {
		return this.lotteryDate;
	}

	public void setLotteryDate(Date lotteryDate) {
		this.lotteryDate = lotteryDate;
	}

	public int getNum1() {
		return this.num1;
	}

	public void setNum1(int num1) {
		this.num1 = num1;
	}

	public int getNum2() {
		return this.num2;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}

	public int getNum3() {
		return this.num3;
	}

	public void setNum3(int num3) {
		this.num3 = num3;
	}

	public int getNum4() {
		return this.num4;
	}

	public void setNum4(int num4) {
		this.num4 = num4;
	}

	public int getNum5() {
		return this.num5;
	}

	public void setNum5(int num5) {
		this.num5 = num5;
	}

	public int getNum6() {
		return this.num6;
	}

	public void setNum6(int num6) {
		this.num6 = num6;
	}

	public int getPrise1Cnt() {
		return this.prise1Cnt;
	}

	public void setPrise1Cnt(int prise1Cnt) {
		this.prise1Cnt = prise1Cnt;
	}

	public int getPrise1Money() {
		return this.prise1Money;
	}

	public void setPrise1Money(int prise1Money) {
		this.prise1Money = prise1Money;
	}

	public int getPrise2Cnt() {
		return this.prise2Cnt;
	}

	public void setPrise2Cnt(int prise2Cnt) {
		this.prise2Cnt = prise2Cnt;
	}

	public int getPrise2Money() {
		return this.prise2Money;
	}

	public void setPrise2Money(int prise2Money) {
		this.prise2Money = prise2Money;
	}

	public int getPrise3Cnt() {
		return this.prise3Cnt;
	}

	public void setPrise3Cnt(int prise3Cnt) {
		this.prise3Cnt = prise3Cnt;
	}

	public int getPrise3Money() {
		return this.prise3Money;
	}

	public void setPrise3Money(int prise3Money) {
		this.prise3Money = prise3Money;
	}

	public int getPrise4Cnt() {
		return this.prise4Cnt;
	}

	public void setPrise4Cnt(int prise4Cnt) {
		this.prise4Cnt = prise4Cnt;
	}

	public int getPrise4Money() {
		return this.prise4Money;
	}

	public void setPrise4Money(int prise4Money) {
		this.prise4Money = prise4Money;
	}

	public int getPrise5Cnt() {
		return this.prise5Cnt;
	}

	public void setPrise5Cnt(int prise5Cnt) {
		this.prise5Cnt = prise5Cnt;
	}

	public int getPrise5Money() {
		return this.prise5Money;
	}

	public void setPrise5Money(int prise5Money) {
		this.prise5Money = prise5Money;
	}

}