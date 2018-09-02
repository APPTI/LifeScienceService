package com.littlefrog.store;


import com.littlefrog.entity.Course;
import com.littlefrog.entity.Lesson;
import com.littlefrog.entity.Post;
import com.littlefrog.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private static final int MAX_SIZE = 100;
    private static Map<Integer, User> userStore = new HashMap<Integer, User>(MAX_SIZE);
    private static Map<Integer, ArrayList<Course>> courseStore = new HashMap<Integer, ArrayList<Course>>();
    private static Map<Integer, ArrayList<Lesson>> lessonStore = new HashMap<Integer, ArrayList<Lesson>>();
    private static Map<Integer, ArrayList<Course>> myCourseStore = new HashMap<Integer, ArrayList<Course>>();//userID-courseList

    public User getById(int id) {
        return userStore.getOrDefault(id, null);
    }

    public void addUser(User user) {
        if (!userStore.containsValue(user)) {
            if (userStore.size() > MAX_SIZE) {
                userStore.clear();
            }
            userStore.put(user.getUserID(), user);
        }
    }

    public void updateUserInfo(int id, User user) {
        if (userStore.containsKey(id)) {
            userStore.replace(id, user);
        } else {
            this.addUser(user);
        }
    }

    public List<Course> getCourseList(int id, int index, int offset) {
        if (courseStore.containsKey(id)) {
            return courseStore.get(id).subList(index, Math.min(index + offset, courseStore.get(id).size()));
        } else {
            return null;
        }
    }

    public void addCourseList(int id, List<Course> courseList) {
        if (courseStore.size() > MAX_SIZE) {
            courseStore.clear();
        }
        courseStore.put(id, (ArrayList) courseList);
    }

    public List<Course> getMyCourseList(int userId, int index, int offset) {
        if (myCourseStore.containsKey(userId)) {
            return myCourseStore.get(userId).subList(index, Math.min(index + offset, myCourseStore.get(userId).size()));
        } else {
            return null;
        }
    }

    public void addMyCourseList(int userId, List<Course> courseList) {
        if (courseStore.size() > MAX_SIZE) {
            courseStore.clear();
        }
        courseStore.put(userId, (ArrayList) courseList);
    }

    public List<Lesson> getLessonList(int id, int index, int offset) {
        if (lessonStore.containsKey(id)) {
            return lessonStore.get(id).subList(index, Math.min(index + offset, lessonStore.get(id).size()));
        } else {
            return null;
        }
    }

    public void addLessonList(int id, List<Lesson> lessonList) {
        if (lessonStore.size() > MAX_SIZE) {
            lessonStore.clear();
        }
        lessonStore.put(id, (ArrayList) lessonList);
    }


}

