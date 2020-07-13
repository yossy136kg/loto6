package jp.yossy.loto6.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.yossy.loto6.form.LotteryResultForm;
import jp.yossy.loto6.repository.Loto6Data;
import jp.yossy.loto6.repository.Loto6DataRepository;

@Controller
@RequestMapping("loto6")
public class Loto6LotteryResultConfirmController {
	static final Logger logger = LoggerFactory.getLogger(Loto6LotteryResultConfirmController.class);

	@Autowired
	Loto6DataRepository loto6DataRepository;

	@Autowired
	MessageSource msg;

	/**
	 * for debug.
	 */
	@SuppressWarnings("unchecked")
	private void modelDump(Model model, String m) {
		logger.debug(" ");
		logger.debug("Model:{}", m);
		Map<String, Object> mm = model.asMap();
		for (Entry<String, Object> entry : mm.entrySet()) {
			logger.debug("key:{}", entry.getKey());
			if (StringUtils.equals(entry.getKey(), "list")) {
				for (Loto6Data data : (List<Loto6Data>) entry.getValue()) {
					logger.debug(
							ToStringBuilder.reflectionToString(data, ToStringStyle.DEFAULT_STYLE));
				}
			}
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * ロト6(LOTO6)抽選結果確認
	 */
	@RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
	public String index(LotteryResultForm form, Model model) {
		Integer minId = loto6DataRepository.minId();
		Integer maxId = loto6DataRepository.maxId();
		Date minLotteryDate = loto6DataRepository.minLotteryDate();
		Date maxLotteryDate = loto6DataRepository.maxLotteryDate();

		form.setMinId(String.format("%04d", minId));
		form.setMaxId(String.format("%04d", maxId));
		SimpleDateFormat fm = new SimpleDateFormat("yyyy年MM月dd日");
		form.setMinLotteryDate(fm.format(minLotteryDate));
		form.setMaxLotteryDate(fm.format(maxLotteryDate));

		return "lotteryresult";
	}
}
