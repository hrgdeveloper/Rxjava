package com.example.hamid.rxjava;

import android.icu.util.TimeUnit;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

//public class CompositeDisposableActivity extends AppCompatActivity {
//
//    /**
//     * Basic Observable, Observer, Subscriber example
//     * Observable emits list of animal names
//     * You can see filter() operator is used to filter out the
//     * animal names that starts with letter `b`
//     */
//
//
//    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//    private String TAG = "testComposite";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_composite_disposable);
//
//        Observable<String> animalsObservable = getAnimalsObservable();
//
//        DisposableObserver<String> animalsObserver = getAnimalsObserver();
//
//        DisposableObserver<String> animalsObserverAllCaps = getAnimalsAllCapsObserver();
//
//        /**
//         * filter() is used to filter out the animal names starting with `b`
//         * */
//        compositeDisposable.add(
//                animalsObservable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .filter(new Predicate<String>() {
//                            @Override
//                            public boolean test(String s) throws Exception {
//                                return s.toLowerCase().startsWith("b");
//                            }
//                        })
//                        .subscribeWith(animalsObserver));
//
//        /**
//         * filter() is used to filter out the animal names starting with 'c'
//         * map() is used to transform all the characters to UPPER case
//         * */
//
//        compositeDisposable.add(
//                animalsObservable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .filter(new Predicate<String>() {
//                            @Override
//                            public boolean test(String s) throws Exception {
//                                return s.toLowerCase().startsWith("c");
//                            }
//                        })
//                        .map(new Function<String, String>() {
//                            @Override
//                            public String apply(String s) throws Exception {
//                                return s.toUpperCase();
//                            }
//                        })
//                        .subscribeWith(animalsObserverAllCaps));
//    }
//
//    private DisposableObserver<String> getAnimalsObserver() {
//        return new DisposableObserver<String>() {
//
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, "Name: " + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "onError: " + e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "All items are emitted!");
//            }
//        };
//    }
//
//    private DisposableObserver<String> getAnimalsAllCapsObserver() {
//        return new DisposableObserver<String>() {
//
//
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, "Name: " + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "onError: " + e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "All items are emitted!");
//            }
//        };
//    }
//
//    private Observable<String> getAnimalsObservable() {
//        return Observable.fromArray(
//                "Ant", "Ape",
//                "Bat", "Bee", "Bear", "Butterfly",
//                "Cat", "Crab", "Cod",
//                "Dog", "Dove",
//                "Fox", "Frog");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        // don't send events once the activity is destroyed
//        compositeDisposable.clear();
//    }
//}
public class CompositeDisposableActivity extends AppCompatActivity {


    /**
     * Basic Observable, Observer, Subscriber example
     * Introduced CompositeDisposable and DisposableObserver
     * The observable emits custom data type (Note) instead of primitive data types
     * ----
     * .map() operator is used to turn the note into all uppercase letters
     * ----
     * You can also notice we got rid of the below declarations
     * Observable<Note> notesObservable = getNotesObservable();
     * DisposableObserver<Note> notesObserver = getNotesObserver();
     */
    private static final String TAG = CompositeDisposableActivity.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composite_disposable);

        // add to Composite observable
        // .map() operator is used to turn the note into all uppercase letters
        disposable.add(getNotesObservable()

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .map(new Function<Note, Note>() {
                    @Override
                    public Note apply(Note note) throws Exception {
                        // Making the note to all uppercase
                        note.setNote(note.getNote().toUpperCase());
                        return note;
                    }
                })
               .subscribeWith(getNotesObserver()));


    }



    private DisposableObserver<Note> getNotesObserver() {
        return new DisposableObserver<Note>() {

            @Override
            public void onNext(Note note) {
                Log.d(TAG, "Note: " + note.getNote());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All notes are emitted!");
            }
        };
    }

    private Observable<Note> getNotesObservable() {
        final List<Note> notes = prepareNotes();

        return Observable.create( new ObservableOnSubscribe<Note>() {
            @Override
            public void subscribe(ObservableEmitter<Note> emitter) throws Exception {
                for (Note note : notes) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(note);
                    }
                }

                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }

    private List<Note> prepareNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "buy tooth paste!"));
        notes.add(new Note(2, "call brother!"));
        notes.add(new Note(3, "watch narcos tonight!"));
        notes.add(new Note(4, "pay power bill!"));

        return notes;
    }

    class Note {
        int id;
        String note;

        public Note(int id, String note) {
            this.id = id;
            this.note = note;
        }

        public int getId() {
            return id;
        }

        public String getNote() {
            return note;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}