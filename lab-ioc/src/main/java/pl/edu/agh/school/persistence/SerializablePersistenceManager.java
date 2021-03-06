package pl.edu.agh.school.persistence;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import pl.edu.agh.school.SchoolClass;
import pl.edu.agh.school.Teacher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class SerializablePersistenceManager extends IPersistenceManager {

    private String teachersStorageFileName;

    public void setTeachersStorageFileName(String teachersStorageFileName) {
        this.teachersStorageFileName = teachersStorageFileName;
    }

    public void setClassStorageFileName(String classStorageFileName) {
        this.classStorageFileName = classStorageFileName;
    }

    private String classStorageFileName;

    @Inject
    public SerializablePersistenceManager(@Named("teachers") String teachersStorageFileName,
                                          @Named("class") String classStorageFileName) {
        this.teachersStorageFileName = teachersStorageFileName;
        this.classStorageFileName = classStorageFileName;
    }

    @Override
    public void saveTeachers(List<Teacher> teachers) {
        if (teachers == null) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(teachersStorageFileName))) {
            oos.writeObject(teachers);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            log.log("There was an error while saving the teachers data", e);
        } finally {
            log.log("Saved teachers data to file " + teachersStorageFileName);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Teacher> loadTeachers() {
        ArrayList<Teacher> res = null;
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(teachersStorageFileName))) {
            res = (ArrayList<Teacher>) ios.readObject();
        } catch (FileNotFoundException e) {
            res = new ArrayList<>();
        } catch (IOException e) {
            log.log("There was an error while loading the teachers data", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        } finally {
            log.log("Loaded teachers data from file " + teachersStorageFileName);
        }
        return res;
    }

    @Override
    public void saveClasses(List<SchoolClass> classes) {
        if (classes == null) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(classStorageFileName))) {
            oos.writeObject(classes);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            log.log("There was an error while saving the classes data", e);
        } finally {
            log.log("Saved classes data to file " + classStorageFileName);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SchoolClass> loadClasses() {
        ArrayList<SchoolClass> res = null;
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(classStorageFileName))) {
            res = (ArrayList<SchoolClass>) ios.readObject();
        } catch (FileNotFoundException e) {
            res = new ArrayList<>();
        } catch (IOException e) {
            log.log("There was an error while loading the classes data", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        } finally {
            log.log("Loaded classes data from file " + classStorageFileName);
        }
        return res;
    }
}
