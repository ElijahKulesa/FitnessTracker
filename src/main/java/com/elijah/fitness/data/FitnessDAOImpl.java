package com.elijah.fitness.data;

import java.sql.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.elijah.fitness.model.Exercise;
import com.elijah.fitness.model.User;
import com.elijah.fitness.model.UserAndPassword;

@Repository("fitnessDAO")
public class FitnessDAOImpl implements FitnessDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Exercise getLatestExerciseForUser(int userId) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Exercise> criteriaQuery = builder.createQuery(Exercise.class);
		Root<Exercise> root = criteriaQuery.from(Exercise.class);
		
		criteriaQuery.select(root).where(builder.equal(root.get("userId"), userId));
		criteriaQuery.orderBy(builder.asc(root.get("date")));
		
		Query query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		query.setMaxResults(1);
		
		List result = query.getResultList();
		
		if(result.isEmpty())
			return null;
		else
			return (Exercise) result.get(0);
	}

	@Override
	public void saveExercise(Exercise e) {
		sessionFactory.getCurrentSession().saveOrUpdate(e);
	}

	@Override
	public void saveUserAccount(UserAndPassword account) {
		//Save is used, rather than saveOrUpdate, since we should only use this method to create new accounts
		sessionFactory.getCurrentSession().save(account);
	}

	@Override
	public String getPasswordHash(String username) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<UserAndPassword> criteriaQuery = builder.createQuery(UserAndPassword.class);
		Root<UserAndPassword> root = criteriaQuery.from(UserAndPassword.class);
		
		criteriaQuery.select(root).where(
				builder.equal(root.get("username"), username));
		
		Query query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		
		List result = query.getResultList();
		
		if(result.isEmpty())
			return null;
		else
			return ((UserAndPassword) result.get(0)).getPasswordHash();
	}

	@Override
	public Exercise getExerciseByDate(int userId, Date date) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Exercise> criteriaQuery = builder.createQuery(Exercise.class);
		Root<Exercise> root = criteriaQuery.from(Exercise.class);
		
		criteriaQuery.select(root).where(
				builder.and(
					builder.equal(root.get("userId"), userId),
					builder.equal(root.get("date"), date)
				));
		
		Query query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		
		List result = query.getResultList();

		if(result.isEmpty())
			return null;
		else
			return (Exercise) result.get(0);
	}

	@Override
	public User getUser(String username) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		
		criteriaQuery.select(root).where(
				builder.equal(root.get("username"), username));
		
		Query query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		User result = (User) query.getSingleResult();
		return result;
	}

	@Override
	public int getUsernameCount(String username) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from(User.class);
		
		criteriaQuery.select(builder.countDistinct(root)).where(builder.equal(root.get("username"), username));
		
		Query query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		long result = (long) query.getSingleResult();
		
		return (int) result;
		
	}
	
	
}
