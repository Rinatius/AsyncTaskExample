package kg.kloop.rinat.asynctasktest;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;

/**
 * Created by admin on 29.07.2016.
 */
public final class Db extends Object{

    private Db(){}

    public static BackendlessCollection<Subject> getAllSubjects(){
        return Backendless.Persistence.of( Subject.class ).find();
    }
}
