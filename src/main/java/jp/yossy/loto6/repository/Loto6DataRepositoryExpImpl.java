package jp.yossy.loto6.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Loto6DataRepositoryExpImpl implements Loto6DataRepositoryExp {

	@Autowired
	EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Loto6Data> result(String[] slctVal) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ");
		sb.append("FROM loto6_data ");
		sb.append("WHERE ");
		for (int i=1; i<slctVal.length; i++) {
			if (i!=1) {
				sb.append(" OR ");
			}
			sb.append("check_num like '%");
			sb.append(slctVal[i]);
			sb.append("%' ");
		}
		sb.append(" ORDER BY id ");

		List<Loto6Data> result = manager.createNativeQuery(sb.toString(), Loto6Data.class).getResultList();

		return result;
	}
}
