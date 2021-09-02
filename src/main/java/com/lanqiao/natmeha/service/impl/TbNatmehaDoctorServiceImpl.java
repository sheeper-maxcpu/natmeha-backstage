package com.lanqiao.natmeha.service.Impl;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaDoctor;
import com.lanqiao.natmeha.service.TbNatmehaDoctorService;
import com.lanqiao.natmeha.dao.TbNatmehaDoctorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*
*/
@Service("tbNatmehaDoctorService")
@Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
public class TbNatmehaDoctorServiceImpl implements TbNatmehaDoctorService{

    @Autowired
    TbNatmehaDoctorDao tbNatmehaDoctorDao;

    @Override
    public Page<TbNatmehaDoctor> selectAllDoctor(TbNatmehaDoctor tbNatmehaDoctor,  int pageNum, int pageSize) {
        return this.tbNatmehaDoctorDao.selectAllDoctor(tbNatmehaDoctor,pageNum,pageSize);
    }

    @Override
    public String getFile(String itemCode){
        return this.tbNatmehaDoctorDao.getFile(itemCode);
    }


//    @Override
//    public TbNatmehaDoctor insertDoctor(TbNatmehaDoctor tbNatmehaDoctor) {
//        return this.tbNatmehaDoctorMapper.insertDoctor(tbNatmehaDoctor);
//    }

    @Override
    public int deleteDoctor(int itemid) {
        return this.tbNatmehaDoctorDao.deleteDoctor(itemid);
    }

    @Override
    public int insertDoctor(TbNatmehaDoctor doctor) {
        return tbNatmehaDoctorDao.insertSelective(doctor);
    }

    @Override
    public TbNatmehaDoctor selectByItemid(Integer itemid) {
        return this.tbNatmehaDoctorDao.selectByItemid(itemid);
    }

    @Override
    public int updateDoctor(TbNatmehaDoctor tbNatmehaDoctor) {
        return this.tbNatmehaDoctorDao.updateDoctor(tbNatmehaDoctor);
    }

    @Override
    public TbNatmehaDoctor getDoctorName(String itemCode) {
        return tbNatmehaDoctorDao.selectDoctor(itemCode);
    }

    @Override
    public List<TbNatmehaDoctor> getDoctor(String doctorName) {
        return tbNatmehaDoctorDao.selectByName(doctorName);
    }


}
