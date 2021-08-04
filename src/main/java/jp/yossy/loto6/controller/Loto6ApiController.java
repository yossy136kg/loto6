package jp.yossy.loto6.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import jp.yossy.loto6.bean.LotteryResultApiData;
import jp.yossy.loto6.form.PastDataForm;
import jp.yossy.loto6.form.PastDataForm.DateSearch;
import jp.yossy.loto6.repository.Loto6Data;
import jp.yossy.loto6.repository.Loto6DataExp;
import jp.yossy.loto6.repository.Loto6DataRepository;
import jp.yossy.loto6.repository.Loto6DataRepositoryExp;

@RestController
public class Loto6ApiController {
	static final Logger logger = LoggerFactory.getLogger(Loto6PastDataController.class);

	@Autowired
	Loto6DataRepository loto6DataRepository;

	@Autowired
	Loto6DataRepositoryExp loto6DataRepositoryExp;

	@Autowired
	MessageSource msg;

	/**
	 * ロト6(LOTO6)過去の抽せん結果取得
	 */
	private Page<Loto6Data> getResults(PastDataForm form) {

		Page<Loto6Data> results = null;
		if (form.getSearchType() == 1) {
			LocalDateTime dt = LocalDateTime.of(form.getSearchYear(), form.getSearchMonth(), form.getSearchDay(), 0, 0,
					0);

			Instant instant = ZonedDateTime.of(dt, ZoneId.systemDefault()).toInstant();
			results = loto6DataRepository.findByLotteryDateBeforeOrderByLotteryDateDesc(Date.from(instant),
					PageRequest.of(0, form.getPages(), Sort.by(Sort.Direction.DESC, "lotteryDate")));
		} else if (form.getSearchType() == 2) {
			results = loto6DataRepository.findByIdLessThanEqualOrderByLotteryDateDesc(form.getSearchTimes(),
					PageRequest.of(0, form.getPages(), Sort.by(Sort.Direction.DESC, "lotteryDate")));
		}

		return results;
	}

	@RequestMapping(value = "/loto6/api/pastdata", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object pastdata(@Validated(DateSearch.class) @ModelAttribute PastDataForm form, BindingResult result) {
		if (result.hasErrors()) {
			return result.getAllErrors();
		}
		if (form.getPages() == null) {
			form.setPages(Loto6PastDataController.INIT_PAGES);
		}
		Page<Loto6Data> results = getResults(form);

		if (results.getContent().size() == 0) {
			String message = msg.getMessage("loto6.list.empty", null, Locale.JAPAN);
			List<String> rt = new ArrayList<String>();
			rt.add(message);
			return rt;
		}
		return results.getContent();
	}

	@RequestMapping(value = "/loto6/api/resultcofirm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object resultConfirm(@RequestParam String slctVal) {
		LotteryResultApiData results = new LotteryResultApiData();

		String[] slctVals = slctVal.split("_");
		List<Loto6Data> resultList = loto6DataRepositoryExp.result(slctVals);

		for (Loto6Data data: resultList) {
			String chkNum = data.getCheckNum();
			String notBonus = chkNum.substring(0, chkNum.length()-3);
			String bonus = chkNum.substring(chkNum.length()-2);
			int place = 0;
			boolean isBonus = false;
			for (int i=1; i<slctVals.length; i++) {
				if (notBonus.indexOf(slctVals[i]) > 0) {
					place += 1;
				}
				isBonus = StringUtils.equals(bonus, slctVals[i]);
			}

			if (place <= 2 && !isBonus) {
				continue;
			}

			Loto6DataExp dataExp = new Loto6DataExp();
			BeanUtils.copyProperties(data, dataExp);
			if (place == 6) {
				dataExp.setPlace(1);
				results.getResult().add(dataExp);
			} else if (place == 5 && isBonus) {
				dataExp.setPlace(2);
				results.getResult().add(dataExp);
			} else if (place == 5) {
				dataExp.setPlace(3);
				results.getResult().add(dataExp);
			} else if (place == 4) {
				dataExp.setPlace(4);
				results.getResult().add(dataExp);
			} else if (place == 3) {
				dataExp.setPlace(5);
				results.getResult().add(dataExp);
			}
		}

		// sort
		Collections.sort(results.getResult(), new Loto6DataExpComparator());

		return results;
	}

	class Loto6DataExpComparator implements Comparator<Loto6DataExp> {
		@Override
		public int compare(Loto6DataExp d1, Loto6DataExp d2) {
			int ret = -1;

			if (d1.getPlace() > d2.getPlace()) {
				ret = 1;
			}
			if (d1.getPlace() == d2.getPlace() && d1.getId() >= d2.getId()) {
				ret = 1;
			}

			return ret;
		}
	}
}
