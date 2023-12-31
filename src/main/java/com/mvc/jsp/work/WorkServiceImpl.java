package com.mvc.jsp.work;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.jpa.user.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.TypedQuery;

@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

	private final WorkRepository repository;
	private final UserRepository userRepository;

	// 등록
	@Override
	public Long register(WorkDto dto) {
		System.out.println("service register");
		Work entity = dtoToEntity(dto);
		System.out.println(dto.getUserCode());
		entity.setListState("모집중");
		repository.save(entity);
		return entity.getListCode();
	}
	
	@Override
	public String getUsernickname(int userCode) {
		return userRepository.findByuserCode(userCode);
	}

	// 상세보기
	@Override
	public WorkDto read(Long listCode) {

		Optional<Work> result = repository.findById(listCode);

		if (result.isPresent()) {
			Work entity = result.get();
			entity.increaselistCnt();
			repository.save(entity);
		}

		return result.isPresent() ? entityToDto(result.get()) : null;
	}

	// 수정
	@Transactional
	@Override
	public Long modify(WorkDto dto) {

		Work work = repository.getOne(dto.getListCode());

		if (work != null) {
			work.changeAmount(dto.getListAmount());
			work.changeCategory(dto.getListCategory());
			work.changeContent(dto.getListContent());
			work.changeEndTime(dto.getListEndTime());
			work.changeFinishDate(dto.getListFinishDate());
			work.changeRegion(dto.getListRegion());
			work.changeStartTime(dto.getListStartTime());
			work.changeTitle(dto.getListTitle());

			repository.save(work);
		}
		return dto.getListCode();
	}

	// 삭제
	@Override
	public void remove(Long listCode) {

		repository.deleteById(listCode);

	}

	// main list 조회순으로 출력
	@Override
	public List<Work> getList8(PageRequestDTO requestDTO) {
		String listState = "모집중";
		List<Work> workList = repository.findByListState(listState);

		return workList;

	}
	
	// main list 조회순으로 출력
	@Override
	public List<Work> getList(String amount,String category,String region) {
		String listState = "모집중";
		if(category==null) {
			category="";
		}else if(region ==null) {
			region="";
		}
		String categoryLike = "%" + category + "%";
		String regionLike = "%" + region + "%";
		List<Work> workList;
		int amountInt = 0;
		
		if(amount ==null||amount=="") {
			workList=repository.findByListCategoryAndListRegionAndListState(categoryLike,regionLike,listState);
		}
		else {
			amountInt = Integer.parseInt(amount);
			if(amountInt > 50000) {
				workList=repository.findByListAmount2AndListCategoryAndListRegionAndListState(50000,categoryLike,regionLike,listState);
			}
			else {
				workList = repository.findByListAmountAndListCategoryAndListRegionAndListState(Integer.parseInt(amount),categoryLike,regionLike,listState);
			}
		}
		
		return workList;

	}
	
	// main list 최근순으로 출력
	@Override
	public List<Work> getSearchList(String search) {
		String listState = "모집중";
		String searchLike = "%" + search + "%";
		List<Work> workList = repository.findByListCategoryOrListTitleOrListRegion(searchLike, listState);
		
		return workList;

	}
	@Override
	public List<Work> getCategoryList(String category) {
		String listState = "모집중";
		String categoryLike = "%" + category + "%";
		List<Work> workList = repository.findByListCategory(categoryLike, listState);
		
		return workList;

	}
}