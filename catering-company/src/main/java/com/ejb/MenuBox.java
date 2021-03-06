package com.ejb;

//import com.model.Dish;

import com.model.Category;
import com.model.Dish;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;


@Singleton
@Startup
public class MenuBox {
    private List<Dish> dishes = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(MenuBox.class);
    @PersistenceContext(name="catering-company")
    private EntityManager em;


    @Lock(READ)
    public List<Dish> getDishes() {
        Query q1 = em.createQuery("Select d from Dish d");
        return q1.getResultList();
    }

    @Lock(WRITE)
    public void addDish(Dish dish) {
        em.persist(dish);
    }


    @Lock(WRITE)
    public void removeDish(int id) {

        Dish dish = em.find(Dish.class, id);
        em.remove(dish);
    }

    @Lock(WRITE)
    public void editDish(Dish dish) {
        em.merge(dish);
    }

    @Lock(READ)
    public List<Category> getCategories() {
        Query q1 = em.createQuery("Select c from Category c");
        return q1.getResultList();
    }


    @Lock(WRITE)
    public void addCategory(Category category) {
        em.persist(category);
    }

    @Lock(WRITE)
    public void editCategory(Category category) {
        em.merge(category);

    }

    @Lock(WRITE)
    public void removeCategory(int id) {
        Category category = em.find(Category.class, id);
        em.remove(category);
    }

    @Lock(WRITE)
    public List<Dish> getDishesFromCategory(int id) {
        Query q1 = em.createQuery("Select d from Dish d where d.category.id = "+ id +"");
        return (List<Dish>)q1;
    }



}
