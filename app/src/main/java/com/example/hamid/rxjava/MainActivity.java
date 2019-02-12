package com.example.hamid.rxjava;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
  int x = 4 ;
  int y = 2;
  int temp = 0 ;
  TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test=(TextView)findViewById(R.id.tv_test);
        Toast.makeText(getApplicationContext(), "salam", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "toast 2", Toast.LENGTH_SHORT).show();
         Observable<String> items = getObservable();

         Observer<String> whatToDo = getObserver();
//        items.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(getObserver());
//        Observable<String> animalsObservable = Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
//        animalsObservable.subs
//        Observable.just("one","two")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Toast.makeText(MainActivity.this, "compelete", Toast.LENGTH_SHORT).show();
//                    }
//                });

        Observable.just(2,4,6,8)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 4 ;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Toast.makeText(MainActivity.this,integer + " ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }
    public Observable<String> getObservable() {
        return Observable.just("hamid" , "saaid" , "mamad");
    }
    public Observable<String> getStringObservable(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            for (int i = 0 ; i < 2 ; i ++) {
                if (!emitter.isDisposed()) {
                    emitter.onNext("salam " + i);
                }
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
            }
        });
  }
    public Observer<String> getObserver() {
        return  new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "compelete", Toast.LENGTH_SHORT).show();
            }
        };
    }
    public void testMikoinm(View view) {
       Observable.range(1,20)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .filter(new Predicate<Integer>() {
                   @Override
                   public boolean test(Integer integer) throws Exception {
                       return integer > 15;
                   }
               })
               .map(new Function<Integer, String>() {
                   @Override
                   public String apply(Integer integer) throws Exception {
                       return integer + " is My number";
                   }
               }).subscribe(new Observer<String>() {
           @Override
           public void onSubscribe(Disposable d) {

           }
           @Override
           public void onNext(String s) {
               Toast.makeText(MainActivity.this, s , Toast.LENGTH_SHORT).show();
           }
           @Override
           public void onError(Throwable e) {

           }
           @Override
           public void onComplete() {
               Toast.makeText(MainActivity.this, "is finished" , Toast.LENGTH_SHORT).show();
           }
       });
    }
}
