package ju.xposed.com.jumodle;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ju.xposed.com.jumodle.roombase.AppDatabase;
import ju.xposed.com.jumodle.roombase.User;
import ju.xposed.com.jumodle.roombase.UserDao;

/**
 * Created by benylwang on 2018/4/9.
 */

public class TestAndroidArcActivity
        extends AppCompatActivity {
    private static final String TAG = "TestAndroidArcActivity";
    private AppDatabase mAppDatabase;
    private UserDao mUserDao;
    private int mIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc);

        mAppDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        mUserDao = mAppDatabase.userDao();
//        getLifecycle()
    }

    public void invoke(View view) {
        Log.e(TAG, "===invoke===" + view.getId());
        switch (view.getId()) {
            case R.id.insert:
                Flowable.create(new FlowableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(FlowableEmitter<Object> flowableEmitter) throws Exception {
                        List<User> users = mUserDao.getAll();
                        if (users.size()>0) {
                            User user = users.get(users.size() - 1);
                            mIndex = user.getUid() + 1;
                        }
                        mUserDao.insertAll(new User(mIndex++, "wang", "yingli"));
                        mUserDao.insertAll(new User(mIndex++, "zhong", "jian"));
                        mUserDao.insertAll(new User(mIndex++, "wang", "xisheng"));
                        mUserDao.insertAll(new User(mIndex++, "wang", "xi"));
                        flowableEmitter.onComplete();
                    }
                }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Object>() {
                            @Override
                            public void onSubscribe(Subscription s) {
                                Log.e(TAG, "===insert==onSubscribe=");
                            }

                            @Override
                            public void onNext(Object o) {
                                Log.e(TAG, "===insert==onNext=");
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Log.e(TAG, "===insert==onError=" + e.toString());
                            }

                            @Override
                            public void onComplete() {
                                Log.e(TAG, "===insert==Complete=");
                            }
                        });


                break;
            case R.id.delete:
                Flowable.create(new FlowableOnSubscribe<Object>() {

                    @Override
                    public void subscribe(FlowableEmitter<Object> e) throws Exception {
                        mUserDao.delete(new User(3, "wang", "xi"));
                        e.onNext(new Object());
                    }
                }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                Log.e(TAG, "===delete==accept=" + o);
                            }
                        }, new Consumer<Throwable>() {

                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                                Log.e(TAG, "===delete==onError=" + throwable.toString());
                            }
                        });

                break;
            case R.id.query:
//                Flowable.create(new FlowableOnSubscribe<Object>() {
//
//                    @Override
//                    public void subscribe(FlowableEmitter<Object> e) throws Exception {
//                        List<User> users = mUserDao.getAll();
//                        for(User u : users) {
//                            Log.e(TAG, "user = " + u);
//                        }
//                        e.onNext(new Object());
//                    }
//                }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<Object>() {
//                            @Override
//                            public void accept(Object o) throws Exception {
//                                Log.e(TAG, "===query==accept=" + o);
//                            }
//                        }, new Consumer<Throwable>() {
//
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                throwable.printStackTrace();
//                                Log.e(TAG, "===query==onError=" + throwable.toString());
//                            }
//                        });


                LiveData<List<User>> listLiveData = mUserDao.getAllByLiveData();
                break;
            case R.id.update:
                Flowable.create(new FlowableOnSubscribe<Object>() {

                    @Override
                    public void subscribe(FlowableEmitter<Object> e) throws Exception {
                        mUserDao.updateUsers(new User(0, "wang", "xi"));
                        e.onNext(new Object());
                    }
                }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                Log.e(TAG, "===update==accept=" + o);
                            }
                        }, new Consumer<Throwable>() {

                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                                Log.e(TAG, "===update==onError=" + throwable.toString());
                            }
                        });

                break;
        }
    }
}
