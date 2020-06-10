package jp.yossy.loto6.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Loto6DataRepository extends JpaRepository<Loto6Data, Integer> {

	public Page<Loto6Data> findByLotteryDateBeforeOrderByLotteryDateDesc(Date lotteryDate, Pageable pages);

	public Page<Loto6Data> findByIdLessThanEqualOrderByLotteryDateDesc(Integer times, Pageable pages);
}
