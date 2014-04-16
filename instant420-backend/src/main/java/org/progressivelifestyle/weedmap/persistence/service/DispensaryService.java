package org.progressivelifestyle.weedmap.persistence.service;

import org.progressivelifestyle.weedmap.persistence.DispensaryDao;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Service
@Transactional
public class DispensaryService {
	@Autowired
	private DispensaryDao dispensaryDao;

	public void setDispensaryDao(DispensaryDao dispensaryDao) {
		this.dispensaryDao = dispensaryDao;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void createDispensary(DispensaryEntity entity){
		dispensaryDao.saveEntity(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateDispensary(DispensaryEntity entity){
		dispensaryDao.updateEntity(entity);
	}
}
