package com.milkharbor.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.milkharbor.dao.MilkCollectionDao;
import com.milkharbor.entity.MilkCollection;

@Service
public class MilkCollectionService {
	
	@Autowired
	private MilkCollectionDao milkCollectionDao;

	public boolean collectMilk(MilkCollection milkCollection) throws ParseException {
		return milkCollectionDao.collectMilk(milkCollection);
	}
	
	public List<MilkCollection> getMilkCollectionDetails(){
		return milkCollectionDao.getMilkCollectionDetails();
	}
	
	public List<MilkCollection> getSupplyMilkDetails(int id){
		return milkCollectionDao.getSupplyMilkDetails(id);
	}
	
	public boolean updateMilkCollection(MilkCollection milkCollection) {
		return milkCollectionDao.updateMilkCollection(milkCollection);
	}
	
	public boolean deleteMIlkDetails(int id) {
		return milkCollectionDao.deleteMIlkDetails(id);
	}
}
