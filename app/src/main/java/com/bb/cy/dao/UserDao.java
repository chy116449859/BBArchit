package com.bb.cy.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertAll(UserBean... users);

    @Delete
    void delete(UserBean user);

    @Update
    void updateUser(UserBean... userBeans);

    @Delete
    void deleteUser(UserBean... userBeans);

    @Query("SELECT * FROM userbean")
    UserBean[] getAllUsers();

    @Query("SELECT * FROM userbean WHERE id IN (:userIds)")
    List<UserBean> loadAllByIds(int[] userIds);

    //传入单个参数
    @Query("SELECT * FROM userbean WHERE age > :minAge")
    UserBean[] loadAllUsersOlderThan(int minAge);

    @Query("SELECT * FROM userbean WHERE age BETWEEN :minAge AND :maxAge")
    UserBean[] loadAllUsersBetweenAges(int minAge, int maxAge);

    @Query("SELECT * FROM userbean WHERE firstName LIKE :search "
            + "OR lastName LIKE :search")
    List<UserBean> findUserWithName(String search);

    @Query("SELECT * FROM userbean WHERE firstName LIKE :first AND "
            + "lastName LIKE :last LIMIT 1")
    UserBean findByName(String first, String last);

//    @Query("SELECT firstName, lastName FROM userbean WHERE region IN (:regions)")
//    public List<NameTupleBean> loadUsersFromRegions(List<String> regions);

}
