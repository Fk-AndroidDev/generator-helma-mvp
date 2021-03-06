/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package <%= appPackage %>.features.category;


import android.util.Log;

import java.lang.ref.WeakReference;

import javax.inject.Inject;
  import okhttp3.ResponseBody;

import <%= appPackage %>.interactors.MyInteractor;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
  import <%= appPackage %>.interactors.remote.error.GeneralApiException;
  import timber.log.Timber;
public class CategoryPresenter implements CategoryContract.Presenter {

    private MyInteractor myInteractor;
    private CompositeSubscription mSubscriptions;
    private WeakReference<CategoryContract.View> mtView;


    @Inject
    public CategoryPresenter(MyInteractor myInteractor) {
        this.myInteractor = myInteractor;
        mSubscriptions = new CompositeSubscription();
    }



    public void getVideoDetailById() {
        Subscription subscription =
                myInteractor.getNavigationDrawerItems()
                          .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                Timber.d("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                              Timber.e("onError");
                              e.printStackTrace();
                              if (e instanceof GeneralApiException) {
                                GeneralApiException generalApiException = (GeneralApiException) e;
                                Timber.e(generalApiException.message());
                              }
                            }

                            @Override
                            public void onNext(ResponseBody s) {
                                Timber.d("onNext");
                            }
                        });
        mSubscriptions.add(subscription);
    }





    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void onViewAttached(CategoryContract.View view) {
        this.mtView=new WeakReference<>(view);
    }

}
